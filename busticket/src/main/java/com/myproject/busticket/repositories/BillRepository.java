package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Customer;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Optional<Bill> findByCustomer(Customer customer);

    // More methods if needed

}
