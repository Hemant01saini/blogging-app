package com.blogapp.dto.request;

import com.blogapp.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class CreatePostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private PostStatus status;

    private Long categoryId;

    private Long createdById;

    private Set<Long> tagIds;

}
