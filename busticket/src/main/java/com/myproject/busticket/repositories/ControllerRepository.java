package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Controller;

@Repository
public interface ControllerRepository extends JpaRepository<Controller, Integer> {

}
