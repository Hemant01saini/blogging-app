package com.blogapp.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long postId;

    private Long parentCommentId;

    @NotBlank
    private String content;
}
