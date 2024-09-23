package com.myproject.busticket.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myproject.busticket.config.VNPayConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/vnpay")
public class VNPayController {

    @Autowired
    private VNPayConfiguration vnpayConfig;

    private static final Logger logger = Logger.getLogger(VNPayController.class.getName());

    @PostMapping("/pay")
    public String pay(HttpServletRequest req) throws IOException {
        logger.info("Received pay request");
        Map<String, String> vnp_Params = buildCommonParams(req, "pay", "other");
        String paymentUrl = buildPaymentUrl(vnp_Params);
        logger.info("Payment URL: " + paymentUrl);
        return buildJsonResponse("00", "success", paymentUrl);
    }

    @PostMapping("/query")
    public String query(HttpServletRequest req) throws IOException {
        logger.info("Received query request");
        Map<String, String> vnp_Params = buildCommonParams(req, "querydr", "Query transaction");
        String paymentUrl = buildPaymentUrl(vnp_Params);
        logger.info("Query URL: " + paymentUrl);
        String responseContent = sendHttpRequest(paymentUrl, "GET", null);
        logger.info("Query response: " + responseContent);
        return buildJsonResponse("00", "success", responseContent);
    }

    @PostMapping("/refund")
    public String refund(HttpServletRequest req) throws IOException {
        logger.info("Received refund request");
        JsonObject vnp_Params = buildRefundParams(req);
        String hash_Data = buildHashData(vnp_Params);
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getSecretKey(), hash_Data);
        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        String responseContent = sendHttpRequest(vnpayConfig.getVnp_ApiUrl(), "POST", vnp_Params.toString());
        logger.info("Refund response: " + responseContent);
        return buildJsonResponse("00", "success", responseContent);
    }

    private Map<String, String> buildCommonParams(HttpServletRequest req, String command, String orderInfo) {
        logger.info("Building common parameters");
        String vnp_Version = "2.1.0";
        long amount = Integer.parseInt(req.getParameter("amount")) * 100;
        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = vnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = vnpayConfig.getIpAddress(req);
        String vnp_TmnCode = vnpayConfig.getVnp_TmnCode();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", orderInfo);

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        String locate = req.getParameter("language");
        vnp_Params.put("vnp_Locale", (locate != null && !locate.isEmpty()) ? locate : "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        logger.info("Common parameters: " + vnp_Params.toString());
        return vnp_Params;
    }

    private String buildPaymentUrl(Map<String, String> vnp_Params) throws IOException {
        logger.info("Building payment URL");
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())).append('&');
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString())).append('&');
            }
        }
        hashData.setLength(hashData.length() - 1); // Remove the last '&'
        query.setLength(query.length() - 1); // Remove the last '&'

        logger.info("Hash data: " + hashData.toString());
        logger.info("Query: " + query.toString());

        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.getSecretKey(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String finalUrl = vnpayConfig.getVnp_PayUrl() + "?" + query.toString();
        logger.info("Final payment URL: " + finalUrl);
        return finalUrl;
    }

    private JsonObject buildRefundParams(HttpServletRequest req) {
        logger.info("Building refund parameters");
        JsonObject vnp_Params = new JsonObject();
        String vnp_Version = "2.1.0";
        String vnp_Command = "refund";
        String vnp_TmnCode = vnpayConfig.getVnp_TmnCode();
        String vnp_TransactionType = req.getParameter("trantype");
        String vnp_TxnRef = req.getParameter("order_id");
        long amount = Integer.parseInt(req.getParameter("amount")) * 100;
        String vnp_Amount = String.valueOf(amount);
        String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnp_TxnRef;
        String vnp_TransactionNo = ""; // Assuming value of the parameter "vnp_TransactionNo" does not exist on your
                                       // system.
        String vnp_TransactionDate = req.getParameter("trans_date");
        String vnp_CreateBy = req.getParameter("user");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        String vnp_IpAddr = vnpayConfig.getIpAddress(req);

        vnp_Params.addProperty("vnp_RequestId", vnpayConfig.getRandomNumber(8));
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_Amount", vnp_Amount);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
        vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        logger.info("Refund parameters: " + vnp_Params.toString());
        return vnp_Params;
    }

    private String buildHashData(JsonObject vnp_Params) {
        logger.info("Building hash data");
        String hashData = String.join("|",
                vnp_Params.get("vnp_RequestId").getAsString(),
                vnp_Params.get("vnp_Version").getAsString(),
                vnp_Params.get("vnp_Command").getAsString(),
                vnp_Params.get("vnp_TmnCode").getAsString(),
                vnp_Params.get("vnp_TransactionType").getAsString(),
                vnp_Params.get("vnp_TxnRef").getAsString(),
                vnp_Params.get("vnp_Amount").getAsString(),
                vnp_Params.get("vnp_TransactionNo").getAsString(),
                vnp_Params.get("vnp_TransactionDate").getAsString(),
                vnp_Params.get("vnp_CreateBy").getAsString(),
                vnp_Params.get("vnp_CreateDate").getAsString(),
                vnp_Params.get("vnp_IpAddr").getAsString(),
                vnp_Params.get("vnp_OrderInfo").getAsString());
        logger.info("Hash data: " + hashData);
        return hashData;
    }

    private String sendHttpRequest(String urlString, String method, String payload) throws IOException {
        logger.info("Sending HTTP request to URL: " + urlString + " with method: " + method);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        if (payload != null) {
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.writeBytes(payload);
                wr.flush();
            }
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        conn.disconnect();
        logger.info("HTTP response content: " + content.toString());
        return content.toString();
    }

    private String buildJsonResponse(String code, String message, String data) {
        logger.info("Building JSON response with code: " + code + ", message: " + message);
        JsonObject job = new JsonObject();
        job.addProperty("code", code);
        job.addProperty("message", message);
        job.addProperty("data", data);
        String jsonResponse = new Gson().toJson(job);
        logger.info("JSON response: " + jsonResponse);
        return jsonResponse;
    }
}