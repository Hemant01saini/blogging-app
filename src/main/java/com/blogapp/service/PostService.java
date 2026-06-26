package com.blogapp.service;

import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.request.UpdatePostRequestDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.entity.Post;
import org.hibernate.sql.Update;

import java.util.List;

public interface PostService
{

    PostResponseDto createPost(
            CreatePostRequestDto postRequestDto);

    List<PostResponseDto> getAllPosts();

    PostResponseDto updatePost(Long id, UpdatePostRequestDto updatePostRequestDto);

    void deletePost(Long id);

    PostResponseDto getPostById(Long id);

    Post getPostEntityById(Long id);

}
