package edu.mum.cs490.project.controller;

import edu.mum.cs490.project.domain.*;
import edu.mum.cs490.project.model.ShoppingCart;
import edu.mum.cs490.project.model.form.CustomerOrderShippingForm;
import edu.mum.cs490.project.model.form.GuestOrderShippingForm;
import edu.mum.cs490.project.model.form.PaymentForm;
import edu.mum.cs490.project.service.CustomerService;
import edu.mum.cs490.project.service.MailService;
import edu.mum.cs490.project.service.OrderService;
import edu.mum.cs490.project.service.ProductService;
import edu.mum.cs490.project.service.impl.MockPaymentServiceImpl;
import edu.mum.cs490.project.utils.AESConverter;
import edu.mum.cs490.project.utils.SignedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//4929127657563699
@Controller
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    private final ProductService productService;

    private final CustomerService customerService;

    private final MockPaymentServiceImpl mockPaymentService;

    private final AESConverter aesConverter;

    private final MailService mailService;

    @Autowired
    public OrderController(OrderService orderService, MockPaymentServiceImpl mockPaymentService, CustomerService customerService,
                           ProductService productService, AESConverter aesConverter, MailService mailService) {
        this.orderService = orderService;
        this.mockPaymentService = mockPaymentService;
        this.productService = productService;
        this.aesConverter = aesConverter;
        this.customerService = customerService;
        this.mailService = mailService;
    }

    //Get all the orders depending on admin and vendor
    @GetMapping("all")
    public String getAllOrders(Model model, HttpSession session, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("orders", this.orderService.findAll());
        } else if (request.isUserInRole("ROLE_CUSTOMER")) {
            return "redirect:/order/customer/all/1";
        }
        return "order/orderlist";
    }

    @GetMapping("customer/all")
    public String redirectCustomerOrderPage() {
        return "redirect:/order/customer/all/1";
    }

    @GetMapping("customer/all/{page}")
    public String getAllCustomerOrderByPage(Model model, @PathVariable("page") int page) {
        Customer customer = (Customer) SignedUser.getSignedUser();
        if (customer == null || customer.getId() == null) {
            return "redirect:/login";
        }
        Page<Order> orders = orderService.findByCustomer_id(customer.getId(), page);
        int current = orders.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, orders.getTotalPages());

        model.addAttribute("orders", orders);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "order/orderlist";
    }

    @GetMapping("customer/{orderId}")
    public String getCustomerOrder(Model model, @PathVariable("orderId") Integer orderId) {
        Order order = orderService.findById(orderId);
        Customer customer = (Customer) SignedUser.getSignedUser();
        if (customer == null || customer.getId() == null) {
            return "redirect:/login";
        }
        if (order == null) {
            return "redirect:/order/customer/all";
        } else if (order.getCustomer().getId().equals(customer.getId())) {
            model.addAttribute("order", order);
            return "order/orderreceipt";
        } else {
            return "redirect:/order/customer/all/1";
        }
    }

    @GetMapping("addToCart/{productId}")
    public String addToCart(HttpSession session, @PathVariable("productId") Integer productId,
                            @RequestParam(value = "quantity", defaultValue = "1") Integer quantity) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        Product product = this.productService.getOne(productId);
        if(product.getQuantity() < quantity){
            quantity = product.getQuantity();
        }
        if (sc == null) {
            sc = new ShoppingCart();
            sc.getOrderDetails().add(new OrderDetail(product, quantity, product.getPrice()));
        } else {
            boolean found = false;
            for (OrderDetail od : sc.getOrderDetails()) {
                if (od.getProduct().getId().equals(productId)) {
                    od.setQuantity(od.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }
            if (!found) {
                sc.getOrderDetails().add(new OrderDetail(product, quantity, product.getPrice()));
            }
        }
        session.setAttribute("shoppingcart", sc);
        return "redirect:/order/shoppingcart";
    }

    @GetMapping("shoppingcart")
    public String getShoppingCart(Model model, HttpServletResponse response, HttpSession session) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        if (sc == null || sc.getOrderDetails().isEmpty()) {
            return "order/emptycart";
        }

        return "order/shoppingcart";
    }

    @PostMapping("shoppingcart/update")
    public @ResponseBody
    String updateCart(HttpSession session, @RequestParam("productid") Integer productid, @RequestParam("updatedquantity") Integer quantity) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        if(sc == null){
            sc = new ShoppingCart();
        }
        sc.update(productid, quantity);

        session.setAttribute("shoppingcart", sc);
        return "success";
    }

    @PostMapping("shoppingcart/remove")
    public @ResponseBody
    String removeCart(HttpSession session, @RequestParam("productid") Integer productid) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        sc.remove(productid);

        session.setAttribute("shoppingcart", sc);
        return "success";
    }


    @GetMapping("checkout")
    public String checkoutFromShoppingCart(Model model, HttpSession session,
                                           @ModelAttribute("customerOrderShippingForm") CustomerOrderShippingForm customerOrderShippingForm) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        if (sc == null || sc.getOrderDetails().isEmpty()) {
            return "order/emptycart";
        }
        Customer customer = (Customer) SignedUser.getSignedUser();
        if (customer == null || customer.getId() == null) {
            return "redirect:/login";
        }
        model.addAttribute("addresses", customerService.findByUser_id(customer.getId()));
        return "order/checkoutcart";
    }

    @PostMapping("checkout")
    public String customerCheckout(@Valid CustomerOrderShippingForm customerOrderShippingForm, BindingResult bindingResult,
                                   @ModelAttribute("paymentForm") PaymentForm paymentForm, HttpSession session, Model model,
                                   HttpServletRequest request) {
        if (request.getParameter("addressId") != null) {
            Address address = this.customerService.findAddressById(Integer.parseInt(request.getParameter("addressId")));
            customerOrderShippingForm.transferAddress(address);
        } else if (bindingResult.hasErrors()) {
            return "order/checkoutcart";
        }
        User user = SignedUser.getSignedUser();
        List<OrderDetail> orderdetails = ((ShoppingCart) session.getAttribute("shoppingcart")).getOrderDetails();
        Order order = new Order();
        order.receiveCustomerShippingForm(user, customerOrderShippingForm);
        order.setOrderDetails(orderdetails);
        model.addAttribute("cards", this.orderService.findCardByUser_id(user.getId()));
        session.setAttribute("checkoutorder", order);
        return "order/submitorder";
    }

    @PostMapping("checkout/submit")
    public String customerOrderPayment(Model model, HttpSession session, @Valid PaymentForm paymentForm, BindingResult bindingResult,
                                       HttpServletRequest request, @AuthenticationPrincipal User user) {
        if (request.getParameter("existing") != null) {
            CardDetail cards = this.orderService.findCardById(Integer.parseInt(request.getParameter("cardId")));
            paymentForm.transferCardDetail(cards, this.aesConverter);
            if (!request.getParameter("cvv").equals(paymentForm.getCvv())) {
                model.addAttribute("cards", this.orderService.findCardByUser_id(user.getId()));
                model.addAttribute("wrongcvv", "Unable to verify your CVV!");
                return "/order/submitorder";
            }
        }

        //clean up paymentForm
        paymentForm.setLast4Digit(paymentForm.getCardNumber().substring(paymentForm.getCardNumber().length() - 4));
        paymentForm.setCardNumber(paymentForm.getCardNumber().replaceAll("\\s", ""));

        //check for paymentform validation error
        if (request.getParameter("month") != null && request.getParameter("year") != null) {
            paymentForm.setCardExpirationDate(request.getParameter("month") + "/" + request.getParameter("year"));
            if (bindingResult.hasErrors()) {
                model.addAttribute("cards", this.orderService.findCardByUser_id(user.getId()));
                model.addAttribute("badcard", "Invalid Card details");
                return "order/submitorder";
            }
        }

        Order order = (Order) session.getAttribute("checkoutorder");
        order.receivePaymentFormAndEncrypt(paymentForm, this.aesConverter);

        //check for product availability
        Map<Product, Integer> productUnavailability = this.orderService.checkProduct(order.getOrderDetails());
        if (!productUnavailability.isEmpty()) {
            return this.orderService.checkProductAvailabilityForCustomer(session, model, productUnavailability, order, user);
        }

        Integer responseCode = orderService.purchase(order);

        if (responseCode != 1) {
            model.addAttribute("cards", this.orderService.findCardByUser_id(user.getId()));
            model.addAttribute("badcard", "Creditcard Declined!");
            return "/order/submitorder";
        }

        orderService.deductProductQuantityAfterPurchase(order);

        order = orderService.saveOrUpdate(order);
        mailService.sendEmailToCustomerAndVendor(order);
        session.removeAttribute("order");
        session.removeAttribute("shoppingcart");
        model.addAttribute("cards", this.orderService.findCardByUser_id(user.getId()));
        model.addAttribute("order", order);
        return "order/ordersuccess";
    }


    @GetMapping("guest/checkout")
    public String guestCheckoutFromShoppingCart(Model model, HttpSession session,
                                                @ModelAttribute("guestOrderShippingForm") GuestOrderShippingForm guestOrderShippingForm) {
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        if (sc == null || sc.getOrderDetails().isEmpty()) {
            return "order/emptycart";
        }

        return "order/guestcheckoutcart";
    }

    @PostMapping("guest/checkout")
    public String guestCheckout(@Valid GuestOrderShippingForm guestOrderShippingForm, BindingResult bindingResult,
                                @ModelAttribute("paymentForm") PaymentForm paymentForm,
                                HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "order/guestcheckoutcart";
        }
        Order order = new Order();
        order.receiveGuestShippingForm(guestOrderShippingForm);
        order.setOrderDetails(((ShoppingCart) session.getAttribute("shoppingcart")).getOrderDetails());

        session.setAttribute("checkoutorder", order);
        return "order/guestsubmitorder";
    }

    @PostMapping("guest/checkout/submit")
    public String guestOrderPayment(Model model, HttpSession session, @Valid PaymentForm paymentForm, BindingResult bindingResult,
                                    @RequestParam("month") String month, @RequestParam("year") String year) {
        //clean up paymentForm
        paymentForm.setCardNumber(paymentForm.getCardNumber().replaceAll("\\s", ""));
        paymentForm.setCardExpirationDate(month + "/" + year);
        paymentForm.setLast4Digit(paymentForm.getCardNumber().substring(paymentForm.getCardNumber().length() - 4));

        //check for paymentform validation error
        if (bindingResult.hasErrors()) {
            model.addAttribute("badcard", "Invalid Card details");
            return "order/guestsubmitorder";
        }
        Order order = (Order) session.getAttribute("checkoutorder");
        order.receivePaymentFormAndEncrypt(paymentForm, this.aesConverter);

        //check for product availability
        Map<Product, Integer> productUnavailability = this.orderService.checkProduct(order.getOrderDetails());
        if (!productUnavailability.isEmpty()) {
            return this.orderService.checkProductAvailabilityForGuest(session, model, productUnavailability, order);
        }

        Integer responseCode = orderService.purchase(order);

        if (responseCode != 1) {
            model.addAttribute("badcard", "Creditcard Declined!");
            return "order/guestsubmitorder";
        }

        orderService.deductProductQuantityAfterPurchase(order);

        order = orderService.saveOrUpdate(order);

        mailService.sendEmailToCustomerAndVendor(order);
        session.removeAttribute("order");
        session.setAttribute("shoppingcart", new ShoppingCart());
        model.addAttribute("order", order);
        return "order/ordersuccess";
    }

    @PostMapping("removeCard")
    public @ResponseBody
    String removeCard(@RequestParam("cardId") String cardId) {
        this.customerService.disableCard(Integer.parseInt(cardId));
        return "success";
    }

    @PostMapping("removeAddress")
    public @ResponseBody
    String removeAddress(@RequestParam("addressId") String addressId) {
        this.customerService.disableAddress(Integer.parseInt(addressId));
        return "success";
    }
}
