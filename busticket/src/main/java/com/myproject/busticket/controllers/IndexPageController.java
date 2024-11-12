package com.myproject.busticket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;


@RequestMapping("/home")
@Controller
public class IndexPageController {
    @GetMapping("/index")
    public String indexPage(Model model) {
        model.addAttribute("tripType", "one-way");
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
