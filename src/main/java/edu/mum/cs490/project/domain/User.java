package edu.mum.cs490.project.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ENABLED;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CardDetail> cards;

    public User() {

    }

    public List<CardDetail> getCards() {
        return cards;
    }

    public void setCards(List<CardDetail> cards) {
        this.cards = cards;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(Status.ENABLED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ENABLED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(Status.ENABLED);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ENABLED);
    }
}
