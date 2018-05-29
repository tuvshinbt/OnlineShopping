package edu.mum.cs490.project.model.form;

import edu.mum.cs490.project.domain.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 5/4/2018
 */
public class CategoryForm implements Serializable {

    private Integer id;
    @NotBlank(message = "Can not be empty")
    private String name;
    private MultipartFile file;
    private Integer parentId;

    public CategoryForm() {
    }

    public CategoryForm(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parentId = category.getParentCategory() != null ? category.getParentCategory().getId() : null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
