package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

}
