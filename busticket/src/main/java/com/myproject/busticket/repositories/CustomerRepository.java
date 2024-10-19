package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByCustomerId(int customerId);

    Optional<Customer> findByFullName(String fullName);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByAddress(String address);

    void deleteByCustomerId(int customerId);
    // Add more methods if needed

}
