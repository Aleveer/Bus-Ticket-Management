package com.myproject.busticket.dto;

import com.myproject.busticket.enums.ControllerStatus;
import com.myproject.busticket.models.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ControllerDTO {
    private int id;
    private Account account;
    private ControllerStatus status;
}
