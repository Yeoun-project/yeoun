package com.example.demo.service;

import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity register(RegisterRequestDto dto) {

        Optional<UserEntity> findUser = userRepository.findByUsername(dto.getUsername());

        if (findUser.isPresent()) throw new RuntimeException("User already exists");

        UserEntity newUser = UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role("ROLE_USER")
                .build();

        return userRepository.save(newUser);
    }

}
