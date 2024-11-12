package com.cv_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("login")
    public String login() {
        return "Login";
    }

    @GetMapping("register")
    public String register() {
        return "Register";
    }
}
