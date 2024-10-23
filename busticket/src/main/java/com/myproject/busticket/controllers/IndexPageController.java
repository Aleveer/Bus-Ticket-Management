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

}
