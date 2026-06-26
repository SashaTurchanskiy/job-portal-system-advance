package com.zosh.job.mapper;

import com.zosh.job.model.User;
import dto.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toUserResponse(User user){
        if (user == null) return null;

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setProfileName(user.getProfileImage());
        response.setRole(user.getRoles());
        response.setStatus(user.getStatus());
        response.setLastLogin(user.getLastLogin());
        response.setCreatedAt(user.getCreatedAt());

        return  response;
    }

    public static List<UserResponse> toDtoList(List<User> users){
        return users.stream().map(UserMapper::toUserResponse).collect(Collectors.toList());
    }
}
