package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FollowResponseDto {

    private Long id;

    private Long followerId;

    private Long followingId;

    private LocalDateTime createdAt;
}
