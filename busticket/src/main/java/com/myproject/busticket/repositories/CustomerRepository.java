package com.myproject.busticket.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.busticket.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
        Customer findByEmail(String email);

        Boolean existsByEmail(String email);

        @Query("SELECT c FROM Customer c WHERE c.name LIKE %?1% OR c.email LIKE %?1% OR c.phone LIKE %?1%")
        Page<Customer> findByNameContainingOrEmailContainingOrPhoneContainingAllIgnoreCase(String searchValue,
                        String searchValue2, Pageable pageable);

        @Query("SELECT c.email, COUNT(bk.bookingId) AS number FROM Customer c JOIN Booking bk ON c.customerId = bk.customer.customerId JOIN Bill bl ON c.customerId = bl.customer.customerId WHERE bl.paymentDate BETWEEN :startDate AND :endDate GROUP BY c.email ORDER BY number DESC")
        List<Object[]> findTopCustomerByBookingsInRange(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate, Pageable pageable);
}