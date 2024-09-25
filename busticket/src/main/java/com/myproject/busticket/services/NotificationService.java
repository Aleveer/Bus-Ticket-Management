package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Notification;
import com.myproject.busticket.repositories.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification getById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification deleteById(int id) {
        Notification notification = getById(id);
        if (notification != null) {
            notificationRepository.deleteById(id);
        }
        return notification;
    }

    public List<Notification> findByUserId(int id) {
        return notificationRepository.findByUserId(id);
    }

}
