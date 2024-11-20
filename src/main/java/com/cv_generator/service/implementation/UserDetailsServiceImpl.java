package com.cv_generator.service.implementation;

import com.cv_generator.model.entities.UserEntity;
import com.cv_generator.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + email + " no existe"));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return User
                .builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(grantedAuthorities)
                .disabled(!userEntity.isEnabled())
                .accountLocked(!userEntity.isAccountNonLocked())
                .accountExpired(!userEntity.isAccountNonExpired())
                .credentialsExpired(!userEntity.isCredentialsNonExpired())
                .build();
    }
}
