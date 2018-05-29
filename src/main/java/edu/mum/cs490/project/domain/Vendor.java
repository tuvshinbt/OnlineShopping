package edu.mum.cs490.project.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Collection;

@Entity
public class Vendor extends User {

    @Column(unique = true)
    private String companyName;
    private String image;

    public Vendor() {
        super();
        this.setStatus(Status.DISABLED);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_VENDOR");
    }
}
