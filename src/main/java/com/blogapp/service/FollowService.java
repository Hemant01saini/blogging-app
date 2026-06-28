package com.blogapp.service;

import com.blogapp.dto.response.FollowResponseDto;
import com.blogapp.dto.response.UserResponseDto;

import java.util.List;

public interface FollowService {

    FollowResponseDto followUser(Long followerId, Long followingId);

    void unFollowUser(Long followerId, Long followingId);

    boolean isFollowing(Long followerId, Long followingId);

    long getFollowersCount(Long userId);

    long getFollowingCount(Long userId);

    List<UserResponseDto> getFollowers(Long userId);

    List<UserResponseDto> getFollowing(Long userId);
}
