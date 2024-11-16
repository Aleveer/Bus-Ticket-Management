package com.myproject.busticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myproject.busticket.models.Role;
import com.myproject.busticket.repositories.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElse(null);
    }

    public Page<Role> getAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
}
