package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.role.Role;
import com.example.demo.vo.UserRegisterInfoVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;

    public RefreshEntity registerByUserInfo(UserRegisterInfoVo vo) {
        String uuid = UUID.randomUUID().toString();
        UserEntity newUser = UserEntity.builder()
                .oAuthId(vo.getOAuthId())
                .name(vo.getName())
                .email(vo.getEmail())
                .phone(vo.getPhone())
                .oAuthPlatform(vo.getOAuthPlatform())
                .role(Role.USER.name())
                .build();

        userRepository.save(newUser);
        return refreshRepository.save(new RefreshEntity(newUser, uuid));
    }
}
