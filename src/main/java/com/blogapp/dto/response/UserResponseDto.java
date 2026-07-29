package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String displayName;
    private String username;
    private String email;
    private String profileImage;
    private String bio;
}
