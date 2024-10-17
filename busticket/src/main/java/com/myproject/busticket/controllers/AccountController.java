package com.myproject.busticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.services.AccountService;

import java.util.List;

@RequestMapping("/accounts")
@RestController
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public ResponseEntity<Account> authenticatedAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Account currentAccount = (Account) authentication.getPrincipal();
        Account account = new Account(currentAccount.getEmail(), currentAccount.getPassword(),
                currentAccount.getFullName(),
                currentAccount.getPhone());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/")
    public ResponseEntity<List<Account>> allAccounts() {
        List<Account> accounts = accountService.allAccounts();
        return ResponseEntity.ok(accounts);
    }
}