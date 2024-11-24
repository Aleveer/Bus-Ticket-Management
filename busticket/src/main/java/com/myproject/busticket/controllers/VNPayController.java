package com.myproject.busticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        VNPayResponse vnPayResponse = vnPayService.createVNPayPayment(amount, "NCB", request);

        Map<String, String> params = VNPayUtil.extractParamsFromUrl(vnPayResponse.paymentURL());
        boolean isPaymentValid = vnPayService.verifyVNPayPayment(params, params.get("vnp_SecureHash"));

        if (isPaymentValid) {
            // TODO: Save payment and other details to database
            return ResponseEntity.ok(vnPayResponse);
        } else {
            return ResponseEntity.badRequest().body(new VNPayResponse("error", "Payment verification failed", ""));
        }
    }

    @GetMapping("/return")
    public ResponseEntity<VNPayResponse> handleVNPayReturn(
            @RequestParam Map<String, String> queryParams) {

        String transactionStatus = queryParams.get("vnp_TransactionStatus");
        if ("00".equals(transactionStatus)) {
            // Xử lý logic thanh toán tại đây
            return ResponseEntity.ok(new VNPayResponse("ok", "success", "Payment successful"));
        } else if("02".equals(transactionStatus)){
            return ResponseEntity.badRequest().body(new VNPayResponse("error", "Payment failed", ""));
        } else {
            return ResponseEntity.badRequest().body(new VNPayResponse("error", "Payment verification failed", ""));
        }
        // Xử lý logic thanh toán tại đây
//        return ResponseEntity.ok(new VNPayResponse("ok", "success", "Payment successful"));
    }
}