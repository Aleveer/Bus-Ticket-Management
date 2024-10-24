package com.myproject.busticket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class IndexPageController {
    @GetMapping("/index")
    public String indexPage() {
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
}
