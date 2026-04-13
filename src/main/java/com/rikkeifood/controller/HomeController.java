package com.rikkeifood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {g
    @GetMapping("/")
    public String home() {
        return "redirect:/food/add";
    }
}