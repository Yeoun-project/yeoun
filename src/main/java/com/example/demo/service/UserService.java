package com.example.demo.service;

import com.example.demo.dto.KakaoUserInfoResponseDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.vo.UserRegisterInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity registerByUserInfo(UserRegisterInfoVo vo) {

        UserEntity newUser = UserEntity.builder()
                .oAuthId(vo.getOAuthId())
                .name(vo.getName())
                .email(vo.getEmail())
                .phone(vo.getPhone())
                .oAuthPlatform(vo.getOAuthPlatform())
                .role("ROLE_USER")
                .build();

        return userRepository.save(newUser);
    }
}
