package edu.mum.cs490.project.model.form.user;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

/**
 * Created by Erdenebayar on 4/26/2018
 */
public class PasswordForm {

    @NotBlank
    private String password;
    @NotBlank
    private String rePassword;
    private boolean valid = true;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    @AssertTrue(message = "Its not same as password field.")
    public boolean getValid() {
        if (this.password != null && this.rePassword != null)
            return this.password.equals(this.rePassword);
        return false;
    }
}
