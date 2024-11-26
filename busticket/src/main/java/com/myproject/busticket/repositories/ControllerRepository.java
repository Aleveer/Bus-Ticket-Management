package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Account;
import com.myproject.busticket.models.Controller;
import java.util.List;

@Repository
public interface ControllerRepository extends JpaRepository<Controller, Integer> {
    List<Controller> findByAccount(Account account);
}
