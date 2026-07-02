package com.zosh.job.service;


import com.zosh.job.model.User;
import com.zosh.job.payload.UpdateUserRequest;
import dto.response.UserResponse;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws Exception;

    User getUserById(Long id) throws Exception;

    List<User> getAllUsers();

    UserResponse updatedProfile(String email, UpdateUserRequest request) throws Exception;

    //admin actions

    UserResponse suspendUser(Long id) throws Exception;

    UserResponse activateUser(Long id) throws Exception;

    UserResponse deleteUser(Long id) throws Exception;
}
