package edu.mum.cs490.project;

import edu.mum.cs490.project.controller.OrderController;
import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.model.ShoppingCart;
import edu.mum.cs490.project.model.form.CustomerOrderShippingForm;
import edu.mum.cs490.project.model.form.PaymentForm;
import edu.mum.cs490.project.service.OrderService;
import edu.mum.cs490.project.service.ProductService;
import edu.mum.cs490.project.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderController orderController;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Mock
    private Principal principal;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        Assert.assertNotNull(orderController);
        Assert.assertNotNull(orderService);
        Assert.assertNotNull(productService);
        Assert.assertNotNull(userService);
    }

    @Test
    @WithUserDetails("yeerick")
    public void addToCard() throws Exception{

        CustomerOrderShippingForm customerShippingForm = new CustomerOrderShippingForm();
        customerShippingForm.setStreet("1000 North 4th Street");
        customerShippingForm.setCity("Fairfield");
        customerShippingForm.setPhoneNumber("2058871599");
        customerShippingForm.setState("IA");
        customerShippingForm.setZipcode("52557");

        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setCardNumber("4929127657563699");
        paymentForm.setCardType("Visa");
        paymentForm.setCardHolderName("yee rick");
        paymentForm.setCvv("123");
        paymentForm.setCardZipcode("52557");
        String month = "05";
        String year = "2018";

        ShoppingCart sc = new ShoppingCart();
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail od = new OrderDetail();
        od.setProduct(productService.getOne(1));
        od.setQuantity(1);
        od.setPrice(150);
        orderDetails.add(od);
        sc.setOrderDetails(orderDetails);

        Order order = new Order();
        order.receiveCustomerShippingForm(userService.getByUsername("yeerick"), customerShippingForm);
        order.setOrderDetails(sc.getOrderDetails());

        mockMvc.perform(get("/order/addToCart/1")).andExpect(redirectedUrl("/order/shoppingcart"));
        mockMvc.perform(get("/order/checkout").principal(principal).sessionAttr("shoppingcart", sc)).andExpect(forwardedUrl("/WEB-INF/jsp/order/checkoutcart.jsp"));
        mockMvc.perform(post("/order/checkout").principal(principal).sessionAttr("shoppingcart", sc)
                .flashAttr("customerOrderShippingForm", customerShippingForm))
                .andExpect(forwardedUrl("/WEB-INF/jsp/order/submitorder.jsp"));

//        mockMvc.perform(post("/order/checkout/submit").principal(principal).sessionAttr("checkoutorder", order).flashAttr("paymentForm", paymentForm)
//                .param("month", month).param("year", year)).andExpect(forwardedUrl("/WEB-INF/jsp/order/ordersuccess.jsp"));

    }

}
