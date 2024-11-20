package com.myproject.busticket.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void delete(Bill bill) {
        billRepository.delete(bill);
    }

    public void deleteAllById(Set<Integer> billIds) {
        billRepository.deleteAllById(billIds);
    }

}
