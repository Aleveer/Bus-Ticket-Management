package com.myproject.busticket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminPageController {
    @GetMapping("/dashboard")
    public String dashBoardPage(){
        return "admin";
    }

    @GetMapping("/addTrip")
    public String addTripPage(){
        return "addTrip";
    }
}
