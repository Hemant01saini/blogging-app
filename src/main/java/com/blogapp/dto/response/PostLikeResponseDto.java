package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostLikeResponseDto {


    private Long id;

    private Long userId;

    private String userName;

    private Long postId;

    private LocalDateTime createdAt;
}
