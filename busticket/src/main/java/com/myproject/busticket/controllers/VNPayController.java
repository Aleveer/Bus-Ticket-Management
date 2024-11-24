package com.myproject.busticket.controllers;

import com.myproject.busticket.models.VNPayRequest;
import com.myproject.busticket.responses.VNPayResponse;
import com.myproject.busticket.services.VNPayService;
import com.myproject.busticket.utilities.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestMapping("/vnpay")
@RestController
public class VNPayController {
    private static final Logger logger = Logger.getLogger(VNPayController.class.getName());

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/createVNPayPayment")
    public ResponseEntity<Map<String, Object>> createVNPayPayment(@RequestBody @Valid VNPayRequest payRequest,
            HttpServletRequest request) {
        long amount = payRequest.amount();
        String orderID = payRequest.orderId();
        logger.info("Creating VNPay payment for amount: " + amount + ", orderID: " + orderID);
        try {
            VNPayResponse vnPayResponse = vnPayService.createVNPayPayment(amount, "NCB", orderID, request);
            Map<String, String> params = VNPayUtil.extractParamsFromUrl(vnPayResponse.paymentURL());
            boolean isPaymentValid = vnPayService.verifyVNPayPayment(params, orderID, params.get("vnp_SecureHash"));
            if (isPaymentValid) {
                logger.info("Payment verification successful for orderID: " + orderID);
                // TODO: Save payment and other details to database:
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("code", vnPayResponse.code());
                response.put("message", vnPayResponse.message());
                response.put("paymentURL", vnPayResponse.paymentURL());
                return ResponseEntity.ok(response);
            } else {
                logger.warning("Payment verification failed for orderID: " + orderID);
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Payment verification failed");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred during VNPay payment processing for orderID: " + orderID, e);
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(500).body(response);
        }
    }
}