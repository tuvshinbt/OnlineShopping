package edu.mum.cs490.project.model.form.user;

import edu.mum.cs490.project.domain.Customer;
import edu.mum.cs490.project.domain.User;
import edu.mum.cs490.project.domain.Vendor;

/**
 * Created by Erdenebayar on 4/27/2018
 */
public class UserFormFactory {

    public UserForm getForm(User user) {
        if (user instanceof Vendor) {
            return new VendorForm((Vendor) user);
        } else if (user instanceof Customer) {
            return new CustomerForm((Customer) user);
        } else
            return null;
    }

    public UserForm getEditForm(User user) {
        if (user instanceof Vendor) {
            return new VendorForm((Vendor) user);
        } else if (user instanceof Customer) {
            return new CustomerForm((Customer) user);
        } else
            return null;
    }

}
