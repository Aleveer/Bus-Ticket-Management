package com.myproject.busticket.controllers;

import com.myproject.busticket.services.RouteCheckpointService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;

import java.util.List;


@RequestMapping("/home")
@Controller
public class IndexPageController {
    RouteCheckpointService routeCheckpointService;
    public IndexPageController(RouteCheckpointService routeCheckpointService) {
        this.routeCheckpointService = routeCheckpointService;
    }
    @GetMapping("/index")
    public String indexPage(Model model) {
        List<String> provinces = routeCheckpointService.getAllProvinces();
        List<String> cities = routeCheckpointService.getAllCities();
        model.addAttribute("tripType", "one-way");
        model.addAttribute("provinces", "provinces");
        model.addAttribute("cities", "cities");
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUsPage() {
        return "about_us";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/search-ticket-info")
    public String searchTicketInfoPage() {
        return "search-ticket-info";
    }
    @GetMapping("/search-billing-info")
    public String searchBillingInfoPage() {
        return "search-billing-info";
    }

    @GetMapping("/admin")
    public  String adminPage(){ return "admin";}
}
