package com.blogapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCommentRequestDto {

    @NotBlank
    private String content;
}
