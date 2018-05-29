package edu.mum.cs490.project.controller;

import edu.mum.cs490.project.domain.Product;
import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.service.CategoryService;
import edu.mum.cs490.project.service.ProductService;
import edu.mum.cs490.project.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final VendorService vendorService;

    private final int PAGE_SIZE = 12;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, VendorService vendorService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.vendorService = vendorService;
    }

    @GetMapping(value = {"list", "search"})
    public String getAllProduct(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer categoryId,
                                @RequestParam(required = false) Integer vendorId,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(required = false) Boolean orderByPrice, Model model) {
        Sort orders = null;
        Page<Product> productList;
        if (orderByPrice != null) {
            orders = orderByPrice ? Sort.by("price").ascending() : Sort.by("price").descending();
            productList = productService.findPage(name, categoryId, vendorId, Status.ENABLED, PageRequest.of(page-1, PAGE_SIZE, orders));
        } else {
            productList = productService.findPage(name, categoryId, vendorId, Status.ENABLED, PageRequest.of(page-1, PAGE_SIZE));
        }
        model.addAttribute("products", productList.getContent());
        model.addAttribute("result", productList);
        //model.addAttribute("products", this.productService.find(name, categoryId, vendorId, Status.ENABLED, orders));
        model.addAttribute("categories", categoryService.find(null, null, Status.ENABLED));
        model.addAttribute("vendors", vendorService.find(null, null, Status.ENABLED));
        model.addAttribute("title", "Products:");
        return "product/list";
    }

    @GetMapping("/{productId}")
    public String getById(Model theModel, @PathVariable("productId") Integer productId) {
        theModel.addAttribute("product", productService.getOne(productId));
        return "product/view";
    }

}
