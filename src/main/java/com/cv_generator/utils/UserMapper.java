package com.cv_generator.utils;

import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.model.dto.UserDTO;
import com.cv_generator.model.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity requestToEntity(LoginRequestDTO loginRequestDTO) {
        return UserEntity
                .builder()
                .email(loginRequestDTO.getEmail())
                .password(passwordEncoder.encode(loginRequestDTO.getPassword()))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
    }
}
