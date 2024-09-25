package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.TransactionHistory;
import com.myproject.busticket.repositories.TransactionHistoryRepository;

@Service
public class TransactionHistoryService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    public List<TransactionHistory> getAll() {
        return transactionHistoryRepository.findAll();
    }

    public TransactionHistory getById(int id) {
        return transactionHistoryRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public TransactionHistory save(TransactionHistory transactionHistory) {
        return transactionHistoryRepository.save(transactionHistory);
    }

    public TransactionHistory deleteById(int id) {
        TransactionHistory transactionHistory = getById(id);
        if (transactionHistory != null) {
            transactionHistoryRepository.deleteById(id);
        }
        return transactionHistory;
    }

    public List<TransactionHistory> getByUserId(int id) {
        return transactionHistoryRepository.findByUserId(id);
    }

    public List<TransactionHistory> getByOrderId(String id) {
        return transactionHistoryRepository.findByPaymentId(id);
    }

    public List<TransactionHistory> getByBookingId(int id) {
        return transactionHistoryRepository.findByBookingId(id);
    }

    public List<TransactionHistory> getByDateBetween(String startDate, String endDate) {
        return transactionHistoryRepository.findByDateBetween(startDate, endDate);
    }
}
