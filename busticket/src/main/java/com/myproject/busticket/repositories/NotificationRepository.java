package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
