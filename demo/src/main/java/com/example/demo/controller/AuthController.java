package com.example.demo.controller;

import com.example.demo.responseDto.LoginResponseDto;
import com.example.demo.responseDto.TokenRequestDto;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google")
    public ResponseEntity<LoginResponseDto> googleLogin(@RequestBody TokenRequestDto tokenRequest) {
        String token = authService.loginWithGoogle(tokenRequest.getIdToken());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
