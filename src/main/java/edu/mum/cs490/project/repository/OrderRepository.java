package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.Order;
import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer_id(Integer customerId);

    List<Order> findByStatusAndCustomer_id(Status status, Integer customerId);

    @Query("select distinct o from Order o join o.orderDetails od WHERE od.product.vendor.id = :vendorId ")
    List<Order> findByVendor_id(@Param("vendorId") Integer VendorId);

    @Query("select distinct o from Order o join o.orderDetails od WHERE od.product.vendor.id = :vendorId AND o.orderDate between :begindate AND :enddate")
    List<Order> findByVendor_idBetweenDate(@Param("vendorId") Integer id, @Param("begindate") Date begin, @Param("enddate") Date end);

    Page<Order> findByCustomer_idOrderByOrderDateDesc(Integer customerId, Pageable pageable);
}
