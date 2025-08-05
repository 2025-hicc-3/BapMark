package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class TestController {

    @GetMapping("/test")
    public String secureEndpoint() {
        return "✅ JWT 인증 완료! 보호된 API 접근 성공";
    }
}
