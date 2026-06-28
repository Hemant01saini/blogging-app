package com.blogapp.service;

import com.blogapp.dto.response.PostLikeResponseDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.PostLike;

import java.util.List;

public interface PostLikeService {

    PostLikeResponseDto likePost(Long userId, Long postId);

    void unLikePost(Long userId, Long postId);

    long getLikeCountByPostId(Long postId);

    List<PostResponseDto> getMostLikedPosts();

    boolean hasUserLikedPost(Long userId, Long postId);

    List<PostResponseDto> getLikedPostsByUser(Long userId);

    List<UserResponseDto> getUsersWhoLikedPost(Long postId);
}
