package edu.mum.cs490.project;

import edu.mum.cs490.project.domain.Customer;
import edu.mum.cs490.project.domain.Product;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.Vendor;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.ProductForm;
import edu.mum.cs490.project.model.form.user.CategroyManagementForm;
import edu.mum.cs490.project.model.form.user.ProductManagmentForm;
import edu.mum.cs490.project.model.form.user.VendorForm;
import edu.mum.cs490.project.model.form.user.VendorSignUpForm;
import edu.mum.cs490.project.service.CategoryService;
import edu.mum.cs490.project.service.ProductService;
import edu.mum.cs490.project.service.VendorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;



@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "vendor")
    public void checkforwardedUrl() throws Exception {

        mockMvc.perform(get("/vendor/product/save")).andExpect(forwardedUrl("/WEB-INF/jsp/vendor/saveProduct.jsp"));

    }
//    @Test
//    @WithUserDetails(value = "vendor")
//    public void isSavingToDatabase() throws Exception {
//
//        ProductForm productForm = new ProductForm();
//
//
//        productForm.setName("Tommy");
//        productForm.setCategoryId(1);
//        productForm.setPrice(625);
//        productForm.setQuantity(4);
//        productForm.setDescription("Coat");
//        productForm.setImage("2.png");
//
//        mockMvc.perform(post("/vendor/saveProduct").flashAttr("productForm", productForm))
//                .andExpect(model().attribute("message", Message.successfullySaved));
//
//       Product products = (Product) productService.getOne(4);
//
//        Assert.assertNotNull(products);
//
//    }
//@Test
//@WithUserDetails(value = "vendor")
//public void checkVendorProductEdit() throws Exception{
//
//    ProductForm productForm = new ProductForm();
//    productForm.setName("Nike Air");
//    productForm.setCategoryId(2);
//    productForm.setPrice(125);
//    productForm.setQuantity(4);
//    productForm.setDescription("Shoes");
//    productForm.setImage("2.png");
//
//    mockMvc.perform(post("/vendor/product/save").flashAttr("productForm", productForm))
//            .andExpect(model().attribute("message", Message.successfullySaved));
//
//    Product product = (Product) productService.find("rolix",null, null, null,null);
//
//    Assert.assertEquals(product.getId(), "2");
//}
//
    @Test
    @WithUserDetails(value = "vendor")
    public void checkAdminProductEdit() throws Exception{

        ProductForm productForm = new ProductForm();
//        productForm.setId(200);
        productForm.setName("Micheal Kors");
        productForm.setCategoryId(5);
        productForm.setPrice(456);
        productForm.setQuantity(1);
        productForm.setDescription("Bag");

        Path path = Paths.get("D:\\resources\\product\\2\\2.png");
        String name = "2.png";
        String originalFileName = "2.png";
        String contentType = "jpg;png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        productForm.setFile(result);
        productForm.setImage("");

        mockMvc.perform(post("/vendor/product/save").flashAttr("productForm", productForm))
                .andExpect(model().attribute("message", Message.successfullySaved));

//        Product product = (Product) productService.find("Micheal Kors",null, null, Status.ENABLED,null).get(0);
//
//        Assert.assertSame(product, productForm);
    }

}
