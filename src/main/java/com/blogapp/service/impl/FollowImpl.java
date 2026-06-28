package com.blogapp.service.impl;

import com.blogapp.dto.response.FollowResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.Follow;
import com.blogapp.entity.User;
import com.blogapp.mapper.FollowMapper;
import com.blogapp.mapper.UserMapper;
import com.blogapp.repository.FollowRepository;
import com.blogapp.service.FollowService;
import com.blogapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowImpl implements FollowService {

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    @Override
    public FollowResponseDto followUser(Long followerId, Long followingId) {

        if (followerId.equals(followingId)){
            throw new RuntimeException("You cannot follow yourself");
        }

        if (followRepository
                .existsByFollowerIdAndFollowingId(followerId, followingId)){
            throw new RuntimeException("Already following this user");
        }

        User follower =
                userService.getUserEntityById(followerId);

        User following =
                userService.getUserEntityById(followingId);

        Follow follow =
                Follow.builder()
                        .follower(follower)
                        .following(following)
                        .build();

        Follow savedFollow =
                followRepository.save(follow);


        return followMapper.toDto(savedFollow);
    }

    @Override
    public void unFollowUser(Long followerId, Long followingId) {

        Follow follow = followRepository.findByFollowerIdAndFollowingId(
                        followerId,
                        followingId)
                .orElseThrow(()->
                        new RuntimeException(
                                "Follow relationship not found"));

        followRepository.delete(follow);
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {

        return followRepository
                .existsByFollowerIdAndFollowingId(
                        followerId, followingId);
    }

    @Override
    public long getFollowersCount(Long userId) {
        return followRepository
                .countByFollowerId(userId);
    }

    @Override
    public long getFollowingCount(Long userId) {
        return followRepository
                .countByFollowingId(userId);
    }

    @Override
    public List<UserResponseDto> getFollowers(Long userId) {

        List<Follow> follows =
                followRepository.findByFollowingId(userId);

        List<UserResponseDto> users =
                new ArrayList<>();

        for (Follow follow : follows) {

            users.add(
                    userMapper.toDto(
                            follow.getFollower()
                    )
            );
        }

        return users;
    }

    @Override
    public List<UserResponseDto> getFollowing(Long userId) {

        List<Follow> follows =
                followRepository.findByFollowerId(userId);

        List<UserResponseDto> users =
                new ArrayList<>();

        for (Follow follow : follows){
            users.add(
                    userMapper.toDto(
                            follow.getFollowing()
                    )
            );
        }
        return users;
    }
}
