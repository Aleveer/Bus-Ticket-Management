package com.myproject.busticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.models.VNPayRequest;
import com.myproject.busticket.responses.VNPayResponse;
import com.myproject.busticket.services.VNPayService;
import com.myproject.busticket.utilities.VNPayUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;

@RequestMapping("/vnpay")
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

        Map<String, String> params = VNPayUtil.extractParamsFromUrl(vnPayResponse.paymentURL());
        boolean isPaymentValid = vnPayService.verifyVNPayPayment(params, orderID, params.get("vnp_SecureHash"));

        if (isPaymentValid) {
            // TODO: Save payment and other details to database:

            return ResponseEntity.ok(vnPayResponse);
        } else {
            return ResponseEntity.badRequest().body(new VNPayResponse("error", "Payment verification failed", ""));
        }
    }
}