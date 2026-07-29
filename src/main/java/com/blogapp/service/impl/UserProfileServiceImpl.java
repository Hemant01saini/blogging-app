package com.blogapp.service.impl;

import com.blogapp.dto.response.MediaResponseDto;
import com.blogapp.dto.response.UserProfileResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.User;
import com.blogapp.repository.FollowRepository;
import com.blogapp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserService userService;

    private final PostService postService;

    private final FollowService followService;

    private final PostLikeService postLikeService;

    private final CommentService commentService;

    private final  MediaService mediaService;


    @Override
    public UserProfileResponseDto getUserProfile(Long userId) {

        User user = userService.getUserEntityById(userId);

        MediaResponseDto profileImage = null;

        try
        {
            profileImage = mediaService.getProfileImage(userId);
        } catch (Exception ignored){
            //if image doesn't exist then null return
        }


        return UserProfileResponseDto.builder()
                .userId(user.getId())
                .displayName(user.getDisplayName())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .profileImage(profileImage != null ? profileImage.getImageUrl() : null)
                .followersCount(followService.getFollowersCount(userId))
                .followingCount(followService.getFollowingCount(userId))
                .postsCount(postService.getPostsCount(userId))
                .totalLikesReceived(postLikeService.getTotalLikesReceived(userId))
                .joinedAt(user.getCreatedAt())
                .build();
    }
}
