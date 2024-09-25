package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Permission;
import com.myproject.busticket.repositories.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission getPermissionById(int id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission deleteById(int id) {
        Permission permission = getPermissionById(id);
        if (permission != null) {
            permissionRepository.deleteById(id);
        }
        return permission;
    }
}
