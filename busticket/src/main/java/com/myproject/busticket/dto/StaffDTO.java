package com.myproject.busticket.dto;

import com.myproject.busticket.enums.StaffStatus;
import com.myproject.busticket.models.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private int staff_id;
    private AccountDTO account;
    private StaffStatus status;
}
