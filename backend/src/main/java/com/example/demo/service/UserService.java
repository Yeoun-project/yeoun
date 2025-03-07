package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.type.Role;
import com.example.demo.vo.UserRegisterInfoVo;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity registerByUserInfo(UserRegisterInfoVo vo) {
        String uuid = UUID.randomUUID().toString();
        UserEntity newUser = UserEntity.builder()
                .oAuthId(vo.getOAuthId())
                .name(vo.getName())
                .email(vo.getEmail())
                .phone(vo.getPhone())
                .oAuthPlatform(vo.getOAuthPlatform())
                .role(Role.ROLE_USER.name())
                .uuid(uuid)
                .build();

        return userRepository.save(newUser);
    }
}
