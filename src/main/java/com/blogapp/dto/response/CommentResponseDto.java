package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {

    private Long id;

    private String content;

    private String author;

    private Long postId;

    private Long parentCommentId;

    private LocalDateTime createdAt;
}
