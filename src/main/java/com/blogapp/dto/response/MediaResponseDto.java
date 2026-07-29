package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MediaResponseDto {

    private Long id;

    private String fileName;

    private String fileType;

    private String imageUrl;

    private LocalDateTime uploadedAt;

    private Long userId;
}
