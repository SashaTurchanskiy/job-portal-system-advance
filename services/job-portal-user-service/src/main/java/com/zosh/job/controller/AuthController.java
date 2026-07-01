package com.zosh.job.controller;

import com.zosh.job.payload.AuthResponse;
import com.zosh.job.payload.LoginRequest;
import com.zosh.job.payload.SignupRequest;
import com.zosh.job.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid SignupRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(request));
    }
}
