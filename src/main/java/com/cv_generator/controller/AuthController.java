package com.cv_generator.controller;

import com.cv_generator.excepcions.EmailAlreadyExistsExcepcion;
import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.service.AuthService;
import com.cv_generator.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @ModelAttribute("loginRequest")
    public LoginRequestDTO loginRequestProvider() {
        return new LoginRequestDTO("", "");
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequestDTO loginRequest) {
        return "Login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("loginRequest") LoginRequestDTO loginRequest, Model model, HttpServletResponse response) {

        try {
            authService.userAuthenticate(loginRequest, response);
            return "redirect:/home";
        }catch (BadCredentialsException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "Login";
        }
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
