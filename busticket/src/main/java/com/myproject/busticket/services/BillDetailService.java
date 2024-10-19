package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.BillDetailRepository;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

}
