package edu.mum.cs490.project.model.form;

import edu.mum.cs490.project.domain.Address;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class CustomerOrderShippingForm implements Serializable {

    private Integer addressId;
    @Pattern(regexp = "\\d{10}", message = "Please enter your Phone number without symbols and space")
    private String phoneNumber;
    @NotBlank(message = "Please fill in your shipping street address!")
    private String street;
    @NotBlank(message = "Please fill in your shipping city address!")
    private String city;
    @NotNull(message = "Please fill in your shipping state address!")
    private String state;
    @Pattern(regexp = "\\d{5}", message = "Please fill in your 5 digit zipcode!")
    private String zipcode;

    public CustomerOrderShippingForm(){}

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public CustomerOrderShippingForm(String phoneNumber, String street, String city, String state, String zipcode) {
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void transferAddress(Address address){
        this.addressId = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.phoneNumber = address.getPhoneNumber();
        this.zipcode = address.getZipcode();
        this.state = address.getState();
    }
}
