package edu.mum.cs490.project.model.form.user;

import javax.validation.constraints.NotBlank;

/**
 * Created by ChanPiseth on 5/01/2018
 */


public class ProductManagmentForm extends VendorForm{
    @NotBlank
    private String name;
    private int quantity;
    private double price;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
