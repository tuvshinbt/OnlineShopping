package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.User;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class UserSignUpForm extends PasswordForm implements Serializable {

    @NotBlank
    private String username;

    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
