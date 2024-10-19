package com.myproject.busticket.services;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Account_Notification;
import com.myproject.busticket.repositories.AccountNotificationRepository;

@Service
public class AccountNotificationService {
    @Autowired
    private AccountNotificationRepository accountNotificationRepository;

    public AccountNotificationService(AccountNotificationRepository accountNotificationRepository) {
        this.accountNotificationRepository = accountNotificationRepository;
    }

    public Account_Notification getById(int id) {
        return accountNotificationRepository.findById(id).orElse(null);
    }

    public Account_Notification save(Account_Notification accountNotification) {
        return accountNotificationRepository.save(accountNotification);
    }

    public List<Account_Notification> allAccountNotifications() {
        List<Account_Notification> accountNotifications = new ArrayList<>();
        accountNotificationRepository.findAll().forEach(accountNotifications::add);
        return accountNotifications;
    }

    public Optional<Account_Notification> findByAccount(Account account) {
        return accountNotificationRepository.findByAccount(account);
    }

    // More methods if needed
}
