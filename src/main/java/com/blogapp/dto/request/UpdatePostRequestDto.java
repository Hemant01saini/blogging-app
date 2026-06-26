package com.blogapp.dto.request;

import com.blogapp.enums.PostStatus;
import lombok.Data;

import java.util.Set;

@Data
public class UpdatePostRequestDto {

    private String title;

    private String content;

    private Long categoryId;

    private Set<Long> tagIds;

    private PostStatus status;
}
