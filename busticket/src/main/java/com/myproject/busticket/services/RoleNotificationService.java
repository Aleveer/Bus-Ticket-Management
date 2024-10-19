package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.repositories.RoleNotificationRepository;

@Service
public class RoleNotificationService {
    @Autowired
    private RoleNotificationRepository roleNotificationRepository;
}
