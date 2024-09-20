package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
