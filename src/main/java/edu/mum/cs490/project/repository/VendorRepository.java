package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Erdenebayar on 4/21/2018
 */
@Repository
public interface VendorRepository extends UserRepository<Vendor> {

    @Query("SELECT a FROM Vendor a WHERE (:username IS NULL OR a.username like %:username%) AND " +
            "(:companyName IS NULL OR a.companyName like %:companyName%) AND " +
            "((:status IS NULL) OR (a.status =:status))")
    List<Vendor> find(@Param("username") String username, @Param("companyName") String companyName, @Param("status") Status status);

    @Query("SELECT a FROM Vendor a WHERE (:username IS NULL OR a.username like %:username%) AND " +
            "(:companyName IS NULL OR a.companyName like %:companyName%) AND " +
            "((:status IS NULL) OR (a.status =:status))")
    Page<Vendor> findPage(@Param("username") String username, @Param("companyName") String companyName, @Param("status") Status status, Pageable pageable);

    Vendor getByCompanyName(String name);
}
