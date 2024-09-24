package com.myproject.busticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.models.VNPayRequest;
import com.myproject.busticket.responses.VNPayResponse;
import com.myproject.busticket.services.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/createVNPayPayment")
    public ResponseEntity<VNPayResponse> createVNPayPayment(@RequestBody @Valid VNPayRequest payRequest,
            HttpServletRequest request) {
        long amount = payRequest.amount();
        String orderID = payRequest.orderId();

        VNPayResponse vnPayResponse = vnPayService.createVNPayPayment(amount, "NCB", orderID, request);

        return ResponseEntity.ok(vnPayResponse);
    }
}