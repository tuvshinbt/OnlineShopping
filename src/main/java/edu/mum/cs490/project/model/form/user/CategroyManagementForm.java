package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Status;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

public class CategroyManagementForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank(message = "Can not be empty")
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
