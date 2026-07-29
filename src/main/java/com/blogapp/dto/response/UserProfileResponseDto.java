package com.blogapp.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDto {


    private Long userId;

    private String displayName;

    private String username;

    private String email;

    private String bio;

    private String profileImage;

    private Long followersCount;

    private Long followingCount;

    private Long postsCount;

    private Long totalLikesReceived;

    private LocalDateTime joinedAt;

}
