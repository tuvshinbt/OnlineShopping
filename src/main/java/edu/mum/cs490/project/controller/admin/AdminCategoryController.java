package edu.mum.cs490.project.controller.admin;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.CategoryForm;
import edu.mum.cs490.project.service.CategoryService;
import edu.mum.cs490.project.service.impl.FileManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by ChanPiseth on 4/28/2018
 */

@Controller
@RequestMapping(value = "/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileManagementService fileManagementService;

//    @Autowired
//    private ServletContext servletContext;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("statuses", Status.values());
        model.addAttribute("categories", categoryService.find(null, null, Status.ENABLED));
        return "admin/category/index";
    }

    @GetMapping("list")
    public String list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) Integer parentId,
                       @RequestParam(required = false, defaultValue = "ENABLED") Status status,
                       Model model) {
        List<Category> categoryList = categoryService.find(name, parentId, status);
        model.addAttribute("list", categoryList);
        return "admin/category/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String addCategory(@RequestParam(value = "id", required = false) Integer categoryId, Model model) {
        // edit category
        model.addAttribute("categories", categoryService.find(null, null, Status.ENABLED));
        CategoryForm categoryForm;
        if (categoryId != null) {
            model.addAttribute("title", "Edit Category");
            categoryForm = new CategoryForm(categoryService.getCategoryById(categoryId));
        } else {
            // create category
            model.addAttribute("title", "Add Category");
            categoryForm = new CategoryForm();
        }
        model.addAttribute("categoryForm", categoryForm);
        return "admin/category/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String addCategoryPost(@Valid @ModelAttribute("categoryForm") CategoryForm categoryForm, BindingResult result, Model model) {
        model.addAttribute("categories", categoryService.find(null, null, Status.ENABLED));
        if (result.hasErrors()) {
            model.addAttribute("message", Message.errorOccurred);
            return "admin/category/create";
        }
        if (categoryForm.getFile() != null && !categoryForm.getFile().isEmpty() && !fileManagementService.checkImageExtension(categoryForm.getFile().getOriginalFilename())) {
            model.addAttribute("message", new Message(Message.Type.ERROR, "File extension must be .jpg or .png!"));
            return "admin/category/create";
        }
        Category category;
        if (categoryForm.getId() != null)
            category = categoryService.getCategoryById(categoryForm.getId());
        else
            category = new Category();
        category.setName(categoryForm.getName());
        category.setParentCategory(categoryForm.getParentId() != null ? categoryService.getCategoryById(categoryForm.getParentId()) : null);
        categoryService.save(category);

        if (categoryForm.getFile() != null) {
            String fileFullName = fileManagementService.createFile(categoryForm.getFile(), "category", category.getId());
            if (fileFullName != null) {
                category.setImage(fileFullName);
                categoryService.save(category);
                model.addAttribute("message", new Message(Message.Type.SUCCESS, "successfully.uploaded"));
            }
        }

//        List<Category> mainCategories = categoryService.find(null, null, Status.ENABLED);
//        servletContext.setAttribute("mainCategories", mainCategories);
        model.addAttribute("message", Message.successfullySaved);
        return "admin/category/create";
    }

    @RequestMapping(value = {"d", "delete"})
    @ResponseBody
    public Message delete(@RequestParam Integer id,
                          Model model) {
        categoryService.delete(id);
        return new Message(Message.Type.SUCCESS, "successfully.deleted");
    }

    @RequestMapping(value = {"changeStatus"})
    @ResponseBody
    public Message changeStatus(@RequestParam Integer id, @RequestParam Status status,
                                Model model) {
        categoryService.changeStatus(id, status);
        return new Message(Message.Type.SUCCESS, "successfully.changed.status");
    }




}
