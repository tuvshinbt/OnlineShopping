package edu.mum.cs490.project.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

@Entity
public class Customer extends User {

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Customer(){
        super();
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_CUSTOMER");
    }

}
