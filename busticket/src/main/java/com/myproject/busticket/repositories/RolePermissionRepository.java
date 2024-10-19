package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Role_Permission;

@Repository
public interface RolePermissionRepository extends JpaRepository<Role_Permission, Integer> {
    Optional<Role_Permission> findByRoleId(int roleId);

    Optional<Role_Permission> findByPermissionId(int permissionId);

    Optional<Role_Permission> findByRoleIdAndPermissionId(int roleId, int permissionId);

    // More methods if needed
}
