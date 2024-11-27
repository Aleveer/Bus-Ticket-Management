package com.myproject.busticket.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myproject.busticket.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %?1% OR c.email LIKE %?1% OR c.phone LIKE %?1%")
    Page<Customer> findByNameContainingOrEmailContainingOrPhoneContainingAllIgnoreCase(String searchValue,
            String searchValue2, Pageable pageable);
}
