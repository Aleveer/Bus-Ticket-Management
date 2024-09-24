package com.myproject.busticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.myproject.busticket.enums.PaymentMethod;
import com.myproject.busticket.enums.PaymentStatus;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false, columnDefinition = "enum('pending', 'completed') default 'pending'")
    private PaymentStatus status;

    @Column(name = "method", nullable = false, columnDefinition = "enum('VNPay', 'Cash') default 'Cash'")
    private PaymentMethod method;
}