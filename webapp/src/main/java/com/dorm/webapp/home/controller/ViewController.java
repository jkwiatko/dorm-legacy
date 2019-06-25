package com.dorm.webapp.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String getLoginView() {
        return "/index.html";
    }

    @GetMapping("/null")
    public String getNull() {
        return "/index.html";
    }
}
