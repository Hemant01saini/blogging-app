package com.blogapp.dto.request;

import com.blogapp.enums.PostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Create Post Request")
public class CreatePostRequestDto {

    @Schema(
            description = "Post title",
            example = "Spring Boot Tutorial",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String title;

    @Schema(
            description = "Post content",
            example = "This is my first blog..."
    )
    @NotBlank
    private String content;

    private PostStatus status;

    private Long categoryId;

    private Long createdById;

    private Set<Long> tagIds;

}
