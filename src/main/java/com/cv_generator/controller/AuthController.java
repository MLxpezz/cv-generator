package com.cv_generator.controller;

import com.cv_generator.excepcions.EmailAlreadyExistsExcepcion;
import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loginRequest")
    public LoginRequestDTO loginRequestProvider() {
        return new LoginRequestDTO("", "");
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("loginRequest") LoginRequestDTO loginRequest) {
        return "Register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("loginRequest") LoginRequestDTO loginRequest, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "Register";
        }

        try {
            userService.newUser(loginRequest);
        }catch (EmailAlreadyExistsExcepcion excepcion) {
            model.addAttribute("errorMessage", excepcion.getMessage());
            return "Register";
        }

        return "redirect:/auth/login";
    }
}
