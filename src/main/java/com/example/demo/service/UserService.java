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
    private final PasswordEncoder passwordEncoder;

    public UserEntity register(RegisterRequestDto dto) {

        //TODO 필요시 아이디 비밀번호 유효성 검사 로직

        Optional<UserEntity> findUser = userRepository.findByUsername(dto.getUsername());

        if (findUser.isPresent()) throw new RuntimeException("User already exists");

        UserEntity newUser = UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("ROLE_USER")
                .build();

        return userRepository.save(newUser);
    }

}
