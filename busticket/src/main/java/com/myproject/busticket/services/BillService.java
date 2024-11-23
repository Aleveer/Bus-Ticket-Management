package com.myproject.busticket.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Bill;
import com.myproject.busticket.repositories.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public Optional<Bill> findByBillId(Bill bill) {
        return billRepository.findById(bill.getBillId());
    }

    public Bill findById(int billId) {
        return billRepository.findById(billId).orElse(null);
    }

    public void delete(Bill bill) {
        billRepository.delete(bill);
    }

    public void deleteAllById(Set<Integer> billIds) {
        billRepository.deleteAllById(billIds);
    }

    public Page<Bill> getAll(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    public void save(Bill bill) {
        billRepository.save(bill);
    }

}
