package com.zosh.job.service.impl;

import com.zosh.job.mapper.UserMapper;
import com.zosh.job.model.User;
import com.zosh.job.payload.UpdateUserRequest;
import com.zosh.job.repository.UserRepository;
import com.zosh.job.service.UserService;
import domain.UserStatus;
import dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new Exception("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws Exception {
       return userRepository.findById(id).orElseThrow(() -> new Exception("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse updatedProfile(String email, UpdateUserRequest request) throws Exception {
        User user = getUserByEmail(email);

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getProfileImage() != null) {
            user.setProfileImage(request.getProfileImage());
        }
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse suspendUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPEND);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) throws Exception {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return UserMapper.toUserResponse(userRepository.save(user));
    }
}
