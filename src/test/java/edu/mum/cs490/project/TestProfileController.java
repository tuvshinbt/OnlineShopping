package edu.mum.cs490.project;

import edu.mum.cs490.project.domain.Customer;
import edu.mum.cs490.project.domain.Vendor;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.user.CardDetailForm;
import edu.mum.cs490.project.model.form.user.CustomerForm;
import edu.mum.cs490.project.model.form.user.PasswordForm;
import edu.mum.cs490.project.model.form.user.VendorForm;
import edu.mum.cs490.project.service.CustomerService;
import edu.mum.cs490.project.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Erdenebayar on 4/27/2018
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "yeerick")
    public void changingUsernameWithExistingUsername() throws Exception{

        CustomerForm customerForm = new CustomerForm();
        customerForm.setFirstName("firstname");
        customerForm.setLastName("lastname");
        customerForm.setUsername("erdenebayar");

        mockMvc.perform(post("/profile/edit").flashAttr("editForm", customerForm))
                .andExpect(model().attributeHasFieldErrors("editForm", "username"));

    }

    @Test
    @WithUserDetails(value = "yeerick")
    public void checkCustomerProfileEdit() throws Exception{

        CustomerForm customerForm = new CustomerForm();
        customerForm.setFirstName("firstname");
        customerForm.setLastName("lastname");
        customerForm.setUsername("yeerick");

        mockMvc.perform(post("/profile/edit").flashAttr("editForm", customerForm))
            .andExpect(model().attribute("message", Message.successfullySaved));

        Customer customer = (Customer) userService.getByUsername("yeerick");

        Assert.assertEquals(customer.getFirstName(), "firstname");

}

    @Test
    @WithUserDetails(value = "akron")
    public void checkVendorProfileEdit() throws Exception{

        VendorForm vendorForm = new VendorForm();
        vendorForm.setCompanyName("companyName");
        vendorForm.setUsername("akron");

        mockMvc.perform(post("/profile/vendor/edit").flashAttr("editForm", vendorForm))
                .andExpect(model().attribute("message", Message.successfullySaved));

        Vendor vendor = (Vendor) userService.getByUsername("akron");

        Assert.assertEquals(vendor.getCompanyName(), "companyName");
    }

    @Test
    @WithUserDetails(value = "vendor")
    public void editCreditCard() throws Exception{

        CardDetailForm cardDetailForm = new CardDetailForm();
        cardDetailForm.setCardNumber("4000300020003002");
        cardDetailForm.setCardType("MASTER");
        cardDetailForm.setCvv("302");
        cardDetailForm.setCardExpirationDate("05/20");
        cardDetailForm.setCardHolderName("V2");
        cardDetailForm.setZipcode("52557");

        mockMvc.perform(post("/profile/card/edit").flashAttr("editCard", cardDetailForm))
                .andExpect(model().attribute("message", Message.successfullySaved));
    }

    @Test
    @WithUserDetails(value = "akron")
    public void editPassword() throws Exception{

        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setPassword("companyName");
        passwordForm.setRePassword("companyName1");

        mockMvc.perform(post("/profile/edit/password").flashAttr("passwordForm", passwordForm))
                .andExpect(model().attribute("message", Message.errorOccurred));
    }


}
