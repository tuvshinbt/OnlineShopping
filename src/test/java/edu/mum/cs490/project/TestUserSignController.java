package edu.mum.cs490.project;

import edu.mum.cs490.project.domain.Customer;
import edu.mum.cs490.project.domain.Vendor;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.user.CustomerForm;
import edu.mum.cs490.project.model.form.user.CustomerSignUpForm;
import edu.mum.cs490.project.model.form.user.VendorForm;
import edu.mum.cs490.project.model.form.user.VendorSignUpForm;
import edu.mum.cs490.project.service.CustomerService;
import edu.mum.cs490.project.service.VendorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Created by Erdenebayar on 4/25/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserSignController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkforwardedUrl() throws Exception {

        mockMvc.perform(get("/login")).andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
        mockMvc.perform(get("/vendor/signup")).andExpect(forwardedUrl("/WEB-INF/jsp/vendor/signUp.jsp"));
        mockMvc.perform(get("/signup")).andExpect(forwardedUrl("/WEB-INF/jsp/signUp.jsp"));
//        Admin test = (Admin) userService.saveOrUpdate(admin);

    }

    @Test
//    @WithMockUser(username="admin",roles={"CUSTOMER","VENDOR", "ADMIN"})
    public void checkExistingUser() throws Exception {

        CustomerSignUpForm signUpForm = new CustomerSignUpForm();
        VendorSignUpForm vendorSignUpForm = new VendorSignUpForm();

        signUpForm.setFirstName("Erdenebayar");
        signUpForm.setLastName("Batsukh");
        signUpForm.setPassword("hello");
        signUpForm.setRePassword("hello");
        signUpForm.setUsername("erdenebayar");
        signUpForm.setEmail("ebatsukh@mum.edu");

        vendorSignUpForm.setCompanyName("Erdenebayar");
        vendorSignUpForm.setPassword("hello");
        vendorSignUpForm.setRePassword("hello");
        vendorSignUpForm.setUsername("erdenebayar");
        vendorSignUpForm.setEmail("ebatsukh@mum.edu");

        Path path = Paths.get("D:\\resources\\product\\4\\4.png");
        String name = "4.png";
        String originalFileName = "4.png";
        String contentType = "jpg;png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        vendorSignUpForm.setFile(result);
        vendorSignUpForm.setCardNumber("4000300020003002");
        vendorSignUpForm.setCardType("MASTER");
        vendorSignUpForm.setCvv("302");
        vendorSignUpForm.setCardExpirationDate("05/20");
        vendorSignUpForm.setCardHolderName("V2");
        vendorSignUpForm.setCardZipcode("52557");

        // Check User Signup to controller
        mockMvc.perform(post("/signup").flashAttr("moduleForm", signUpForm))
                .andExpect(model().attributeHasFieldErrors("moduleForm", "username"));
        //moduleForm is from SignController

        // Check Vendor Signup
        mockMvc.perform(post("/vendor/signup").flashAttr("moduleForm", vendorSignUpForm))
                .andExpect(model().attributeHasFieldErrors("moduleForm", "username"));
    }

    @Test
    public void isSavingToDatabase() throws Exception {

        CustomerSignUpForm signUpForm = new CustomerSignUpForm();
        VendorSignUpForm vendorSignUpForm = new VendorSignUpForm();

        signUpForm.setFirstName("Erdenebayar");
        signUpForm.setLastName("Batsukh");
        signUpForm.setPassword("hello");
        signUpForm.setRePassword("hello");
        signUpForm.setUsername("uniqueUser1");
        signUpForm.setEmail("ebatsukh@mum.edu");

        vendorSignUpForm.setCompanyName("Erdenebayar");
        vendorSignUpForm.setPassword("hello");
        vendorSignUpForm.setRePassword("hello");
        vendorSignUpForm.setUsername("uniqueUser2");
        vendorSignUpForm.setEmail("ebatsukh@mum.edu");

        Path path = Paths.get("D:\\resources\\product\\4\\4.png");
        String name = "4.png";
        String originalFileName = "4.png";
        String contentType = "jpg;png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        vendorSignUpForm.setFile(result);
        vendorSignUpForm.setCardNumber("4000300020003002");
        vendorSignUpForm.setCardType("MASTER");
        vendorSignUpForm.setCvv("302");
        vendorSignUpForm.setCardExpirationDate("05/20");
        vendorSignUpForm.setCardHolderName("V2");
        vendorSignUpForm.setCardZipcode("52557");

        mockMvc.perform(post("/signup").flashAttr("moduleForm", signUpForm))
                .andExpect(model().attribute("message", Message.successfullySaved));

        mockMvc.perform(post("/vendor/signup").flashAttr("moduleForm", vendorSignUpForm))
                .andExpect(model().attribute("message", Message.successfullySaved));


        Customer customers = (Customer) customerService.getByUsername("uniqueUser1");
        Vendor vendors = (Vendor) vendorService.getByUsername("uniqueUser2");

        Assert.assertNotNull(customers);
        Assert.assertNotNull(vendors);
    }
}
