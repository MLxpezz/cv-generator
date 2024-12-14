package com.cv_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashBoardController {

    @GetMapping("home")
    public String home() {
        return "Home";
    }

    @GetMapping("savedTemplates")
    public String savedTemplates() {
        return "SavedTemplates";
    }

    @GetMapping("/newTemplate")
    public String newTemplate() {
        return "cv-templates/HarvardTemplate";
    }
}
