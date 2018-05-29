package edu.mum.cs490.project.model;

import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    List<OrderDetail> orderDetails;

    public ShoppingCart(){
        this.orderDetails = new ArrayList<OrderDetail>();
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double calculateTotalPrice(){
        double sum = 0;
        for(OrderDetail orderdetail : this.orderDetails){
            sum += orderdetail.calculateTotalPrice();
        }
        return sum;
    }

    public double calculateTax(){
        return calculateTotalPrice() * 0.07;

    }

    public double calculateTotalPriceWithTax(){
        return calculateTotalPrice() * 1.07;
    }

    public void update(Integer productid, int quantity){
        int len = this.getOrderDetails().size();
        for(int i = 0; i< len; i++){
            if (this.getOrderDetails().get(i).getProduct().getId().equals(new Integer(productid))) {
                if (quantity == 0) {
                    this.getOrderDetails().remove(i);
                } else {
                    this.getOrderDetails().get(i).setQuantity(quantity);
                }
            }
        }
    }

    public void remove(Integer productid){
        for(int i = 0; i < orderDetails.size(); i++){
            if(orderDetails.get(i).getProduct().getId().equals(productid)){
                orderDetails.remove(i);
            }
        }
    }

    public void convergeProductAvailability(Map<Product,Integer> productUnavailability){
        for(int i = 0 ; i<this.getOrderDetails().size() ; i++){
            Product product = this.getOrderDetails().get(i).getProduct();
            if(productUnavailability.containsKey(product)){
                this.getOrderDetails().get(i).setQuantity(productUnavailability.get(product));
                if(product.getQuantity() == 0){
                    this.getOrderDetails().remove(i);
                }
            }
        }
    }
}
