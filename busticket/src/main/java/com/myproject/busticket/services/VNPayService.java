package com.myproject.busticket.services;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.config.VNPayConfiguration;
import com.myproject.busticket.enums.PaymentMethod;
import com.myproject.busticket.enums.PaymentStatus;
import com.myproject.busticket.models.Payment;
import com.myproject.busticket.repositories.PaymentRepository;
import com.myproject.busticket.responses.VNPayResponse;
import com.myproject.busticket.utilities.VNPayUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class VNPayService {
    @Autowired
    private VNPayConfiguration vnPayConfiguration;

    @Autowired
    private PaymentRepository paymentRepository;

    public VNPayResponse createVNPayPayment(long amount, String bankCode, String orderID, HttpServletRequest request) {
        long vnpAmount = amount * 100L;

        Map<String, String> vnpParamsMap = vnPayConfiguration.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(vnpAmount));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_TxnRef", orderID);

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfiguration.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfiguration.getVnp_PayUrl() + "?" + queryUrl;
        return new VNPayResponse("ok", "success", paymentUrl);
    }

    public boolean verifyVNPayPayment(Map<String, String> params, String orderId, String secureHash) {
        // Filter out the vnp_SecureHash parameter
        Map<String, String> filteredParams = params.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("vnp_SecureHash"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Recalculate the hash
        String calculatedHash = VNPayUtil.hmacSHA512(vnPayConfiguration.getSecretKey(),
                VNPayUtil.getPaymentURL(filteredParams, false));

        // Compare secure hashes
        if (!calculatedHash.equals(secureHash)) {
            return false;
        }

        // Get payment status from params
        String paymentRef = params.get("vnp_TxnRef");
        String paymentStatus = params.get("vnp_ResponseCode");
        Double price = Double.valueOf(params.get("vnp_Amount"));

        // Fetch the order using the orderId
        // Order order = orderRepository.findById(orderId)
        // .orElseThrow(() -> new BookingException(BookingErrorCode.ORDER_NOT_FOUND,
        // HttpStatus.NOT_FOUND));

        // TODO: Fetch the order using bookings / booking details table:

        PaymentStatus paymentStatusEnum = "00".equals(paymentStatus) ? PaymentStatus.completed : PaymentStatus.pending;

        Payment payment = new Payment(paymentRef, price, paymentStatusEnum, PaymentMethod.VNPay);
        paymentRepository.save(payment);
        paymentRepository.findById(payment.getId()).orElseThrow(() -> {
            throw new RuntimeException("Payment not found");
        });

        return "00".equals(paymentStatus);
    }
}
