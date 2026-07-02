package com.zosh.job.controller;

import com.zosh.job.mapper.UserMapper;
import com.zosh.job.model.User;
import com.zosh.job.payload.UpdateUserRequest;
import com.zosh.job.service.UserService;
import dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(@RequestHeader ("X-User-Email") String email) throws Exception {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @PutMapping("/update/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestHeader("X-User-Email") String email,
            @RequestBody UpdateUserRequest request) throws Exception {
        UserResponse updatedUser = userService.updatedProfile(email, request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) throws Exception {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toUserResponse(user));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(UserMapper.toDtoList(userService.getAllUsers()));
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<UserResponse> suspendUser(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.suspendUser(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.activateUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable Long id) throws Exception {
        UserResponse deletedUser = userService.deleteUser(id);
        return ResponseEntity.ok(deletedUser);
    }

}
