package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Customer;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class CustomerForm extends UserForm implements Serializable {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public CustomerForm() {
    }

    public CustomerForm(Customer customer) {
        super(customer);
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
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
}
