package com.cv_generator.service;

import com.cv_generator.model.dto.LoginRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    void userAuthenticate(LoginRequestDTO loginRequestDTO, HttpServletResponse response);

    void userLogout(HttpServletRequest request, HttpServletResponse response);
}
