package com.example.demo.controller;

import java.net.http.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/api/1")
    public ResponseEntity<?> test(){
        return ResponseEntity.status(200).body("Hello World");
    }
}
