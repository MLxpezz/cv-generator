package com.cv_generator.service;

import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.model.dto.UserDTO;

public interface UserService {

    void newUser(LoginRequestDTO loginRequestDTO);

    UserDTO updateUser(UserDTO userDTO, Long id);

    void deleteUser(Long id);

    UserDTO getUserById(Long id);

    boolean existsUser(String email);
}
