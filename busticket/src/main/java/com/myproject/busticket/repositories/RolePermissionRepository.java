package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.myproject.busticket.models.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    RolePermission findByRoleIdAndPermissionId(int roleId, int permissionId);

    List<RolePermission> findByRoleId(int roleId);

    List<RolePermission> findByPermissionId(int permissionId);
}
