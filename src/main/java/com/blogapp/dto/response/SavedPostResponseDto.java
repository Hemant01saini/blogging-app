package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SavedPostResponseDto {

    private Long id;

    private Long userId;

    private Long postId;

    private LocalDateTime createdAt;


}
