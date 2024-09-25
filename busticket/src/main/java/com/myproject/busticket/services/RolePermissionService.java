package com.myproject.busticket.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.RolePermission;
import com.myproject.busticket.repositories.RolePermissionRepository;

@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    public List<RolePermission> getAll() {
        return rolePermissionRepository.findAll();
    }

    public RolePermission getById(int id) {
        return rolePermissionRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public RolePermission save(RolePermission rolePermission) {
        return rolePermissionRepository.save(rolePermission);
    }

    public RolePermission deleteById(int id) {
        RolePermission rolePermission = getById(id);
        if (rolePermission != null) {
            rolePermissionRepository.deleteById(id);
        }
        return rolePermission;
    }

    public List<RolePermission> getByRoleId(int roleId) {
        return rolePermissionRepository.findByRoleId(roleId);
    }

    public List<RolePermission> getByPermissionId(int permissionId) {
        return rolePermissionRepository.findByPermissionId(permissionId);
    }
}
