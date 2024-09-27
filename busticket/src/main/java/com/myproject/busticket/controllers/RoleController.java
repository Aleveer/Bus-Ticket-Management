package com.myproject.busticket.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.busticket.models.Role;
import com.myproject.busticket.services.RoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> allRoles() {
        List<Role> roles = roleService.getAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/createNewRole")
    public Map<String, Object> addNewRole(@RequestBody Map<String, Object> roleMap) {
        Map<String, Object> response = new HashMap<>();

        try {
            Role role = new Role();
            role.setName(roleMap.get("name").toString());
            Role newRole = roleService.save(role);
            response.put("success", true);
            response.put("message", "Role added successfully");
            response.put("role", newRole);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }

        return response;
    }

}
