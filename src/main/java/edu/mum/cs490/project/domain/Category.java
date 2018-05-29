package edu.mum.cs490.project.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;
    private String name;
    private String image;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ENABLED;
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childCategories;

    @Transient
    private Set<Integer> parentIds = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }

    public Category() {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public Set<Integer> getParentIds() {
        this.parentIds.add(id);
        for (Category category : childCategories) {
            this.parentIds.addAll(category.getParentIds());
        }

        return parentIds;
    }
}
