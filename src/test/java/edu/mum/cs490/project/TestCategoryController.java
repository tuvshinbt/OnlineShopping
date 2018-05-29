package edu.mum.cs490.project;


import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.user.CategoryForm;
import edu.mum.cs490.project.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * Created by ChanPiseth on 5/06/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestCategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void checkforwardedUrl() throws Exception {
        mockMvc.perform(get("/admin/category/create")).andExpect(forwardedUrl("/WEB-INF/jsp/admin/category/index.jsp"));
    }
    @Test
    @WithUserDetails(value = "Phones, Tablets")
    public void checkExistingProduct() throws Exception {
        CategoryForm categoryobj = new CategoryForm();
        categoryobj.setName("Phones, Tablets");
        categoryobj.setParentCategory("");

        mockMvc.perform(post("/admin/category/create").flashAttr("categoryForm",categoryobj))
                .andExpect(model().attributeHasFieldErrors("categoryForm","name"));

    }

    @Test
    @WithUserDetails(value = "Phones, Tablets")
    public void isSavingToDatabase() throws Exception {
        CategoryForm categoryobj = new CategoryForm();
        categoryobj.setName("Phones, Tablets");
        categoryobj.setParentCategory("");
        mockMvc.perform(post("/admin/category/create").flashAttr("categoryForm",categoryobj))
                .andExpect(model().attribute("message", Message.errorOccurred));

      }
    }
