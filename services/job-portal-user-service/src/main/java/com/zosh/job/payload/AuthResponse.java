package com.zosh.job.payload;

import dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String jwt;
    private String title;
    private String message;
    private UserResponse user;
}
