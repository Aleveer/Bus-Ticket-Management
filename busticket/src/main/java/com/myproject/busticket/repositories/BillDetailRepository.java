package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Bill;
import com.myproject.busticket.models.Bill_Detail;
import com.myproject.busticket.enums.TicketType;

@Repository
public interface BillDetailRepository extends JpaRepository<Bill_Detail, Integer> {
    Optional<Bill_Detail> findByBill(Bill bill);

    Optional<Bill_Detail> findByTicketType(TicketType ticketType);

    Optional<Bill_Detail> findById(int id);

    void deleteByBill(Bill bill);

    // More methods if needed
}
