package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class UserForm implements Serializable{

    private Integer id;
    @NotBlank
    private String username;
    @Email
    private String email;

    public UserForm() {
    }

    public UserForm(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
