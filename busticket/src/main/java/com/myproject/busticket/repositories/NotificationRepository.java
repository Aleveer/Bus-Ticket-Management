package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Optional<Notification> findByAccount(Account account);

    Optional<Notification> findByNotificationId(int notificationId);

    // More methods if needed

}
