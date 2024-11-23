package com.myproject.busticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.myproject.busticket.models.Account;
import com.myproject.busticket.services.AccountService;

import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/me")
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

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> allAccounts() {
        List<Account> accounts = accountService.allAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/easy-bus/account-detail/{accountId}")
    public String getAccountDetail(@PathVariable int accountId, Model model) {
        Account account = accountService.getById(accountId);
        if (account == null) {
            model.addAttribute("message", "Account not found");
            return "error";
        }

        model.addAttribute("account", account);
        return "account-detail";
    }

    @GetMapping("/easy-bus/update-account/{accountId}")
    public String updateAccount(@PathVariable int accountId,Model model){
        Account account = accountService.getById(accountId);
        if(account == null){
            model.addAttribute("errorMessage", "Account not found.");
            return "redirect:/easy-bus/account-management";
        }
        model.addAttribute("account",account);
        return "update-account";
    }
}
