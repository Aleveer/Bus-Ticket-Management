package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.myproject.busticket.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    Customer findByEmail(String email);
}
