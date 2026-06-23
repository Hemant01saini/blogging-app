package com.blogapp.dto.response;

import com.blogapp.enums.PostStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponseDto {

    private Long id;

    private String title;

    private String content;

    private PostStatus status;

    private String author;

    private String category;

    private Long likesCount;

    private Long commentsCount;

    private LocalDateTime createdAt;

}
