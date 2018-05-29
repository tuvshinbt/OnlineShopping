package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.Admin;
import edu.mum.cs490.project.domain.CardDetail;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VendorService extends UserService<Vendor>{

    List<Vendor> find(String username, String companyName, Status status);

    Page<Vendor> findPage(String username, String companyName, Status status, Pageable pageable);

    Vendor getByCompanyName(String companyName);

    @Transactional
    Integer transferFee(CardDetail vendorCardDetail, Vendor vendor);
}
