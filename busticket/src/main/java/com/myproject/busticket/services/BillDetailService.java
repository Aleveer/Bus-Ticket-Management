package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Bill_Detail;
import com.myproject.busticket.models.Trip;
import com.myproject.busticket.repositories.BillDetailRepository;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    public List<Bill_Detail> findByTrip(Trip trip) {
        return billDetailRepository.findByTrip(trip);
    }

    public void delete(Bill_Detail bill) {
        billDetailRepository.delete(bill);
    }

    public void deleteAll(List<Bill_Detail> billDetails) {
        billDetailRepository.deleteAll(billDetails);
    }
}
