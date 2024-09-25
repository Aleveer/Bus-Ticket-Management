package com.myproject.busticket.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    List<TransactionHistory> findByUserId(int id);

    List<TransactionHistory> findByPaymentId(String order);

    List<TransactionHistory> findByBookingId(int id);

    // Search between two dates:
    @Query(value = "SELECT * FROM transaction_history WHERE payment_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<TransactionHistory> findByDateBetween(String startDate, String endDate);

    // Implement more methods / queries here if needed.
}
