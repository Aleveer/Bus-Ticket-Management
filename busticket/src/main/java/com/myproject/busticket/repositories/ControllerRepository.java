package com.myproject.busticket.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Controller;

@Repository
public interface ControllerRepository extends JpaRepository<Controller, Integer> {
    Optional<Controller> findByAccount(Account account);
    // Add more methods if needed

}
