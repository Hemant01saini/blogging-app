package com.blogapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {

    private Long postId;

    private String title;

    private String content;

    private String authorName;

    private String profileImage;

    private String categoryName;

    private Long likesCount;

    private Long commentsCount;

    private LocalDateTime createdAt;


}
