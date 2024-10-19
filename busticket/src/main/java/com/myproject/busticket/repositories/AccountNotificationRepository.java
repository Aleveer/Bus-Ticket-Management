package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Account_Notification;
import com.myproject.busticket.models.Notification;

@Repository
public interface AccountNotificationRepository extends JpaRepository<Account_Notification, Integer> {
    Optional<Account_Notification> findByAccount(Account account);

    Optional<Account_Notification> findByNotification(Notification notification);

    // More methods if needed
}