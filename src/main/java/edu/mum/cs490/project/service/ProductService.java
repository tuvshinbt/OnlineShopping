package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.Product;
import edu.mum.cs490.project.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Pagmaa on 4/23/2018
 */

@Transactional(readOnly = true)
public interface ProductService {

    Product getOne(Integer id);

    @Transactional
    @PreAuthorize("#product.vendor.id == principal.id or hasRole('ROLE_ADMIN')")
    Product saveOrUpdate(Product product);

    @Transactional
    @PreAuthorize("#hasAnyRole('ROLE_ADMIN' ,'ROLE_VENDOR')")
    void delete(Integer id);

    @Transactional
    @PreAuthorize("#hasAnyRole('ROLE_ADMIN' ,'ROLE_VENDOR')")
    void changeStatus(Integer id, Status status);

    @Transactional
    List<Product> find(String name, Integer categoryId, Integer vendorId, Status status, Sort sort);

    Page<Product> findPage(String name, Integer categoryId, Integer vendorId, Status status, Pageable pageable);
}
