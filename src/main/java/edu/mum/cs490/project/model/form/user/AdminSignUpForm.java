package edu.mum.cs490.project.model.form.user;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class AdminSignUpForm extends UserSignUpForm implements Serializable {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

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
