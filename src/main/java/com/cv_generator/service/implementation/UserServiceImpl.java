package com.cv_generator.service.implementation;

import com.cv_generator.model.dto.LoginRequestDTO;
import com.cv_generator.model.dto.UserDTO;
import com.cv_generator.model.entities.UserEntity;
import com.cv_generator.repository.UserRepository;
import com.cv_generator.service.UserService;
import com.cv_generator.utils.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void newUser(LoginRequestDTO loginRequestDTO) {
        userRepository.save(userMapper.requestToEntity(loginRequestDTO));
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
