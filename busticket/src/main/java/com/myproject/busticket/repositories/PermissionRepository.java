package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByPermissionId(int permissionId);

    Optional<Permission> findByPermissionName(String permissionName);
    // More methods if needed

}
