package com.zosh.job.service.impl;

import com.zosh.job.mapper.UserMapper;
import com.zosh.job.model.User;
import com.zosh.job.payload.AuthResponse;
import com.zosh.job.payload.LoginRequest;
import com.zosh.job.payload.SignupRequest;
import com.zosh.job.repository.UserRepository;
import com.zosh.job.service.AuthService;
import domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signup(SignupRequest request) throws Exception {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new Exception("Email already registered : " + request.getEmail());
        }
        if (request.getRole() == UserRole.ROLE_ADMIN){
            throw new Exception("cannot self register as role admin");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRole())
                .phone(request.getPhone())
                .lastLogin(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("welcome" + savedUser.getFullName());
        res.setMessage("Register successfully");
        res.setJwt("jwt");
        res.setUser(UserMapper.toUserResponse(savedUser));

        return res;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
