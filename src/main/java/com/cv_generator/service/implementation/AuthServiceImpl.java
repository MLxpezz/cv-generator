package com.cv_generator.service.implementation;

import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.service.AuthService;
import com.cv_generator.utils.jwt.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void userAuthenticate(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
            );

            String token = jwtUtils.generateToken(authentication);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);

            response.addCookie(cookie);

            System.out.println("token generado y puesto en las cookies: " + token);
            System.out.println("cookie: " + cookie.getName() + " y valor: " + cookie.getValue());
        }catch (AuthenticationException exception) {
            throw new BadCredentialsException("Las credenciales son incorrectas");
        }
    }

    @Override
    public void userLogout() {

    }
}
