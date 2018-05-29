package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.*;
import edu.mum.cs490.project.framework.observer.*;
import edu.mum.cs490.project.framework.template.TransactionTemplate;
import edu.mum.cs490.project.framework.template.impl.PurchaseTemplateImpl;
import edu.mum.cs490.project.model.ShoppingCart;
import edu.mum.cs490.project.repository.AddressRepository;
import edu.mum.cs490.project.repository.CardDetailRepository;
import edu.mum.cs490.project.repository.OrderRepository;
import edu.mum.cs490.project.repository.ProductRepository;
import edu.mum.cs490.project.service.MailService;
import edu.mum.cs490.project.service.OrderService;
import edu.mum.cs490.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRespository;
    private final CardDetailRepository cardDetailRepository;
    private final PaymentService paymentService;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final MailService mailService;

    private static final int PAGE_SIZE = 5;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CardDetailRepository cardDetailRepository,
                            PaymentService paymentService, AddressRepository addressRepository, ProductRepository productRepository, MailService mailService) {
        this.orderRespository = orderRepository;
        this.cardDetailRepository = cardDetailRepository;
        this.paymentService = paymentService;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.mailService = mailService;
    }

    @Value("${card.detail.id.oss}")
    private Integer cardDetailIdOSS;

    @Value("${card.detail.id.tax}")
    private Integer cardDetailIdTAX;

    @Override
    public List<Order> findAll() {
        return this.orderRespository.findAll();
    }

    @Override
    public List<Order> findallEnabledByCustomer_id(Integer customerId) {
        return this.orderRespository.findByStatusAndCustomer_id(Status.ENABLED, customerId);
    }


    //Order Manipulation
    @Override
    public List<Order> findByCustomer_id(Integer customerId) {
        return this.orderRespository.findByCustomer_id(customerId);
    }

    @Override
    public List<Order> findByVendor_id(Integer vendorId) {
        return this.orderRespository.findByVendor_id(vendorId);
    }

    @Override
    public Order findById(Integer orderId) {
        return this.orderRespository.findById(orderId).orElse(null);
    }

    @Override
    public Page<Order> findByCustomer_id(Integer customerId, int page) {
        return this.orderRespository.findByCustomer_idOrderByOrderDateDesc(customerId, PageRequest.of(page - 1, PAGE_SIZE));
    }

    @Override
    public List<Order> findByVendor_idBetweenDate(Integer vendorId, Date begin, Date end) {
        return this.orderRespository.findByVendor_idBetweenDate(vendorId, begin, end);
    }

    @Override
    public List<CardDetail> findCardByUser_id(Integer userId) {
        return this.cardDetailRepository.findByOwner_idAndStatus(userId, Status.ENABLED);
    }

    @Override
    public CardDetail findCardById(Integer cardId) {
        return this.cardDetailRepository.findById(cardId).orElse(null);
    }

    @Override
    public Map<Product, Integer> checkProduct(List<OrderDetail> orderDetails) {
        Map<Product, Integer> productUnavailable = new HashMap<>();
        for (OrderDetail od : orderDetails) {
            Product p = this.productRepository.getOne(od.getProduct().getId());
            if (p == null) {
                productUnavailable.put(od.getProduct(), 0);
            } else if (p.getQuantity() < od.getQuantity()) {
                productUnavailable.put(od.getProduct(), p.getQuantity());
            }
        }

        return productUnavailable;
    }

    @Override
    public Order saveOrUpdate(Order order) {
        order.setOrderDate(new Date());
        order.setShippingDate(new Date());
        for(OrderDetail od : order.getOrderDetails()){
            od.setOrder(order);
        }
        this.cardDetailRepository.save(order.getCard());
        this.addressRepository.save(order.getAddress());
        return this.orderRespository.save(order);
    }

    @Override
    public Integer purchase(Order order) {
        CardDetail OSSCardDetail = cardDetailRepository.findById(cardDetailIdOSS).get();
        CardDetail TAXCardDetail = cardDetailRepository.findById(cardDetailIdTAX).get();
        TransactionTemplate purchaseTemplate = getPurchaseTemplate(order, OSSCardDetail, TAXCardDetail);
        return purchaseTemplate.process();
    }

    @Override
    public String checkProductAvailabilityForCustomer(HttpSession session, Model model, Map<Product, Integer> productUnavailability,
                                                      Order order, User user) {
        List<String> errorMessages = order.getProductAvailabilityError(productUnavailability);
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        sc.convergeProductAvailability(productUnavailability);
        order.convergeProductAvailability(productUnavailability);
        model.addAttribute("errorMessages", errorMessages);
        model.addAttribute("cards", this.findCardByUser_id(user.getId()));
        session.setAttribute("shoppingcart", sc);
        session.setAttribute("order", order);
        if (sc.getOrderDetails().isEmpty()) {
            return "/order/emptycart";
        }
        return "/order/submitorder";
    }

    @Override
    public String checkProductAvailabilityForGuest(HttpSession session, Model model, Map<Product, Integer> productUnavailability,
                                                   Order order) {
        List<String> errorMessages = order.getProductAvailabilityError(productUnavailability);
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        sc.convergeProductAvailability(productUnavailability);
        order.convergeProductAvailability(productUnavailability);
        model.addAttribute("errorMessages", errorMessages);
        session.setAttribute("shoppingcart", sc);
        session.setAttribute("order", order);
        if (sc.getOrderDetails().isEmpty()) {
            return "/order/emptycart";
        }
        return "/order/guestsubmitorder";
    }

    @Override
    public void deductProductQuantityAfterPurchase(Order order) {
        for (OrderDetail od : order.getOrderDetails()) {
            this.productRepository.deductProductAfterPurchase(od.getQuantity(), od.getProduct().getId());
        }
    }


    private TransactionTemplate getPurchaseTemplate(Order order, CardDetail OSSCardDetail, CardDetail taxCardDetail) {
        NotifierSubject notifierSubject = new NotifierSubject();
        TransferSubject transferSubject = new TransferSubject();
        transferSubject.addObserver(new TransferToVendorObserver(order, OSSCardDetail, paymentService, cardDetailRepository));
        transferSubject.addObserver(new TransferToTAXObserver(order, OSSCardDetail, taxCardDetail, paymentService));
        TransactionTemplate purchaseTemplate = new PurchaseTemplateImpl(order, OSSCardDetail, notifierSubject, transferSubject, paymentService);
        return purchaseTemplate;
    }
}
