package dto.response;

import domain.UserRole;
import domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String profileName;
    private String profileImage;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
