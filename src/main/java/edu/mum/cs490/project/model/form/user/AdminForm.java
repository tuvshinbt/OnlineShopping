package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Admin;
import edu.mum.cs490.project.domain.Customer;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class AdminForm extends UserForm implements Serializable {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public AdminForm() {
    }

    public AdminForm(Admin admin) {
        super(admin);
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
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
