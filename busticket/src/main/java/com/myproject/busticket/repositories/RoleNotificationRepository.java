package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Role_Notification;

@Repository
public interface RoleNotificationRepository extends JpaRepository<Role_Notification, Integer> {
    Optional<Role_Notification> findByRoleId(int roleId);

    Optional<Role_Notification> findByNotificationId(int notificationId);
}
