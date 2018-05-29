package edu.mum.cs490.project.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne //(fetch = FetchType.LAZY)
    private Order order;
    @OneToOne
    private Product product;
    private int quantity;
    private double price;

    @Transient
    private Date beginDate;

    @Transient
    private Date endDate;

    public OrderDetail(){}

    public OrderDetail(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double calculateTotalPrice(){
        return this.quantity * this.price;
    }

    public double getVendorEarning(){
        return calculateTotalPrice() * 0.8;
    }
}
