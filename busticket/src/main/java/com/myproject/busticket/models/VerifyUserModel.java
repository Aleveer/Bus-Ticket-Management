package com.myproject.busticket.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserModel {
    private String email;
    private String verificationCode;
}