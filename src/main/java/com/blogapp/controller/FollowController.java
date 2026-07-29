package com.blogapp.controller;

import com.blogapp.dto.response.FollowResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
@Tag(
        name = "Follow APIs",
        description = "Operations related to follow system"
)
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "Follow user by ID")
    @PostMapping("/{followerId}/{followingId}")
    public ResponseEntity<FollowResponseDto> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(followService.followUser(
                followerId, followingId));
    }
    @Operation(summary = "unfollow user by ID")
    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<String> unFollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        followService.unFollowUser(
                followerId, followingId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "check follower by ID")
    @GetMapping("/check/{followerId}/{followingId}")
    public ResponseEntity<Boolean> isFollowing(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {

        return ResponseEntity.ok(followService.isFollowing(
                followerId, followingId));
    }

    @Operation(summary = "get follower Count by ID")
    @GetMapping("/followers-count/{userId}")
    public ResponseEntity<Long> getFollowersCount(
            @PathVariable Long userId) {


        return ResponseEntity.ok(followService.getFollowersCount(userId));
    }

    @Operation(summary = "Get following count by ID")
    @GetMapping("/following-count/{userId}")
    public ResponseEntity<Long> getFollowingCount(
            @PathVariable Long userId) {

        return ResponseEntity.ok(followService.getFollowingCount(userId));
    }

    @Operation(summary = "Get followers by ID")
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<UserResponseDto>> getFollowers(
            @PathVariable Long userId) {

        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @Operation(summary = "Get following by ID")
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<UserResponseDto>> getFollowing(
            @PathVariable Long userId) {

        return ResponseEntity.ok(followService.getFollowing(userId));
    }
}
