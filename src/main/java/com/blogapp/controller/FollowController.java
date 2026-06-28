package com.blogapp.controller;

import com.blogapp.dto.response.FollowResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followerId}/{followingId}")
    public FollowResponseDto followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return followService.followUser(
                followerId, followingId);
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public String unFollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        followService.unFollowUser(
                followerId, followingId);

        return "User unfollowed successfully";
    }

    @GetMapping("/check/{followerId}/{followingId}")
    public boolean isFollowing(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return followService.isFollowing(
                followerId, followingId);
    }

    @GetMapping("/followers-count/{userId}")
    public long getFollowersCount(
            @PathVariable Long userId) {


        return followService.getFollowersCount(userId); }

    @GetMapping("/following-count/{userId}")
    public long getFollowingCount(
            @PathVariable Long userId) {

        return followService.getFollowingCount(userId);
    }

    @GetMapping("/followers/{userId}")
    public List<UserResponseDto> getFollowers(
            @PathVariable Long userId) {

        return followService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public List<UserResponseDto> getFollowing(
            @PathVariable Long userId) {

        return followService.getFollowing(userId);
    }
}
