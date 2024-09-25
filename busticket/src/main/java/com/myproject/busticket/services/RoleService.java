package com.myproject.busticket.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Role;
import com.myproject.busticket.repositories.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    // Note: This method uses for both add and update
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role deleteById(int id) {
        Role role = getById(id);
        if (role != null) {
            roleRepository.deleteById(id);
        }
        return role;
    }
}
