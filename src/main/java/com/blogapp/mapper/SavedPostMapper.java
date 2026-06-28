package com.blogapp.mapper;

import com.blogapp.dto.response.SavedPostResponseDto;
import com.blogapp.entity.SavedPost;
import org.springframework.stereotype.Component;

@Component
public class SavedPostMapper {

    public SavedPostResponseDto toDto(
            SavedPost savedPost
    ){

        return SavedPostResponseDto.builder()
                .id(savedPost.getId())
                .userId(savedPost.getUser().getId())
                .postId(savedPost.getPost().getId())
                .createdAt(savedPost.getCreatedAt())
                .build();
    }
}
