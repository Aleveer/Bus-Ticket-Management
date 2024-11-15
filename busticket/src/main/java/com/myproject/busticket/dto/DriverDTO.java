package com.myproject.busticket.dto;

import com.myproject.busticket.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private int driverId;
    private AccountDTO account;
    private DriverStatus status;
}
