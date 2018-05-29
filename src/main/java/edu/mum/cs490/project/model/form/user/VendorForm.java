package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Vendor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by Erdenebayar on 4/21/2018
 */
public class VendorForm extends UserForm implements Serializable {

    @NotBlank
    private String companyName;

    public VendorForm() {
    }

    public VendorForm(Vendor vendor) {
        super(vendor);
        this.companyName = vendor.getCompanyName();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
