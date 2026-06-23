package com.blogapp.service;

import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.response.PostResponseDto;

import java.util.List;

public interface PostService
{

    PostResponseDto createPost(
            CreatePostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

}
