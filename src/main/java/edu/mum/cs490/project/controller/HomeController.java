package edu.mum.cs490.project.controller;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Product;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.Vendor;
import edu.mum.cs490.project.service.CategoryService;
import edu.mum.cs490.project.service.ProductService;
import edu.mum.cs490.project.service.UserService;
import edu.mum.cs490.project.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
public class HomeController {

    public final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    @Autowired
    private VendorService vendorService;

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("/")
    public String getHome(Model model){


        List<Product> productList = productService.find(null, null, null, Status.ENABLED, null);
        List<Category> mainCategories = categoryService.find(null, null, Status.ENABLED);
        List<Category> categories = categoryService.findAllActiveInList();
        List<Vendor> vendors = vendorService.find(null, null, Status.ENABLED);

        productList.addAll(productList);
        model.addAttribute("products", productList);
        model.addAttribute("vendors", vendors);
        model.addAttribute("categories", categories);

        servletContext.setAttribute("mainCategories", mainCategories);

        return "index";
    }

    @RequestMapping("/contact")
    public String getContact(){
        return "contact";
    }

}
