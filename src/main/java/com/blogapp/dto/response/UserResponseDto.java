package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;


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
