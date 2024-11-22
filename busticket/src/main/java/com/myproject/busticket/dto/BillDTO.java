package com.myproject.busticket.dto;

import com.myproject.busticket.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private int billId;
    private CustomerDTO customer;
    private PaymentMethod paymentMethod;
    private String paymentDate;
}
