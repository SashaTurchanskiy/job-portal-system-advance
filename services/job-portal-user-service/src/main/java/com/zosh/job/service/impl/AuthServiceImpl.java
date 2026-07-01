package com.zosh.job.service.impl;

import com.zosh.job.mapper.UserMapper;
import com.zosh.job.model.User;
import com.zosh.job.payload.AuthResponse;
import com.zosh.job.payload.LoginRequest;
import com.zosh.job.payload.SignupRequest;
import com.zosh.job.repository.UserRepository;
import com.zosh.job.security.CustomUserDetailsService;
import com.zosh.job.security.JwtProvider;
import com.zosh.job.service.AuthService;
import domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

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

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        AuthResponse res = new AuthResponse();
        res.setTitle("welcome" + savedUser.getFullName());
        res.setMessage("Register successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toUserResponse(savedUser));

        return res;
    }

    @Override
    public AuthResponse login(LoginRequest request) throws Exception {
        Authentication authentication = authenticate(
                request.getEmail(), request.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail());

        String jwt = jwtProvider.generateToken(authentication, user.getId());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse res = new AuthResponse();
        res.setTitle("welcome" + user.getFullName());
        res.setMessage("Login successfully");
        res.setJwt(jwt);
        res.setUser(UserMapper.toUserResponse(user));

        return res;
    }

    private Authentication authenticate(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid email or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
