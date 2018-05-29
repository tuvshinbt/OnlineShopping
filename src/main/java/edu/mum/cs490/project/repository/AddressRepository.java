package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.Address;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser_idAndStatus(Integer userId, Status status);

    @Query("update Address a set a.status = 'DISABLED' where a.id = :addressId")
    @Modifying
    void disableAddress(@Param("addressId") Integer addressId);
}
