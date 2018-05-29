package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Status;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by ChanPiseth on 5/03/2018
 */

public class CategoryForm implements Serializable {

    @NotBlank
    private String name;
    @NotBlank
    private String parentCategory;
    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
