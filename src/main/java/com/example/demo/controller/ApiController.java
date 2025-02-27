package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController { // 자체 토큰 테스트용 임시 컨트롤러

    @GetMapping("/api/1")
    public String temp() {
        return "jwt token was passed";
    }

}
