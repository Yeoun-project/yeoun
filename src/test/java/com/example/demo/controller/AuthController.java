package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserRegisterInfoVo;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthController {
    @Autowired
    private UserService userService;

    private String socialLoginPK = "pk-from-test-social-login";
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void registerUser() {
        // given
        UserRegisterInfoVo registerInfo = UserRegisterInfoVo.builder()
            .oAuthId(socialLoginPK)
            .oAuthPlatform("test")
            .email("test@test.com")
            .name("test_name")
            .build();

        // when
        UserEntity registerUser = userService.registerByUserInfo(registerInfo);

        // then
        Assertions.assertAll(
            () -> Assertions.assertTrue(registerUser.getOAuthPlatform().equals("test")),
            () -> Assertions.assertTrue(registerUser.getOAuthId().equals("pk-from-test-social-login")),
            () -> Assertions.assertTrue(registerUser.getName().equals("test_name")),
            () -> Assertions.assertTrue(registerUser.getEmail().equals("test@test.com")),
            () -> Assertions.assertTrue(
                ((ArrayList<GrantedAuthority>)registerUser.getAuthorities())
                    .stream().anyMatch(authority->authority.getAuthority().equals("USER")))

        );
    }

    @Test
    @Order(2)
    public void socialLogin() {
        // given

        // when
        Optional<UserEntity> user = userRepository.findByOAuthId(socialLoginPK);

        // then
        Assertions.assertTrue(user.isPresent());
    }
}
