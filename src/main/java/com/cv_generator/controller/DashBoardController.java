package com.cv_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class DashBoardController {

    @GetMapping
    public String home() {
        return "Home";
    }
}
