package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByAccountId(int accountId);
}
