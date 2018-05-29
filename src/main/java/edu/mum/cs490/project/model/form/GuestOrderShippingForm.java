package edu.mum.cs490.project.model.form;

import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GuestOrderShippingForm extends CustomerOrderShippingForm {


    @NotBlank(message="Please fill in your First Name")
    private String firstName;
    @NotBlank(message = "Please fill in your Last name")
    private String lastName;
    @Email(message = "Please fill in an valid email")
    private String email;

    public GuestOrderShippingForm() {
    }

    public GuestOrderShippingForm(@Pattern(regexp = "\\d{10}") String phoneNumber, String street, String city, @NotNull String state, @Pattern(regexp = "\\d{5}") String zipcode, @NotBlank String firstName, @NotBlank String lastName, @Email String email) {
        super(phoneNumber, street, city, state, zipcode);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
