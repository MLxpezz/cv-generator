package com.cv_generator.service.implementation;

import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.model.dto.UserDTO;
import com.cv_generator.model.entities.UserEntity;
import com.cv_generator.repository.UserRepository;
import com.cv_generator.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void newUser(LoginRequestDTO loginRequestDTO) {
        UserEntity newUser = UserEntity
                .builder()
                .email(loginRequestDTO.getEmail())
                .password(loginRequestDTO.getPassword())
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        userRepository.save(newUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }
}
