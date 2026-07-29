package com.blogapp.service;

import com.blogapp.dto.response.*;


public interface SearchService {

    PageResponse<PostResponseDto> searchPosts(
            String keyword,
            int page,
            int size
    );

    PageResponse<CategoryResponseDto> searchCategories(
            String keyword,
            int page,
            int size);

    PageResponse<TagResponseDto> searchTags(
            String keyword,
            int page,
            int size);

    PageResponse<UserResponseDto> searchUsers(
            String keyword,
            int page,
            int size);
}
