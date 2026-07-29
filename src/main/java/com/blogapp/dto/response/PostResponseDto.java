package com.blogapp.dto.response;

import com.blogapp.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Post Response")
public class PostResponseDto {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Spring Boot Tutorial")
    private String title;

    private String content;

    private PostStatus status;

    @Schema(example = "Hemant Saini")
    private String author;

    private String category;

    private Long likesCount;

    private Long commentsCount;

    private LocalDateTime createdAt;

}
