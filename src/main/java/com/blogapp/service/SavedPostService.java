package com.blogapp.service;

import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.SavedPostResponseDto;
import com.blogapp.repository.SavedPostRepository;

import java.util.List;

public interface SavedPostService {

    SavedPostResponseDto savePost(
            Long userId,
            Long postId
    );

    void unSavePost(
            Long userId,
            Long postId
    );

    boolean isPostSaved(
            Long userId,
            Long postId
    );

    List<PostResponseDto> getSavedPostsByUser(
            Long userId
    );


}
