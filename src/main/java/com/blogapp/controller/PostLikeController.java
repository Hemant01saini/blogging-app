package com.blogapp.controller;

import com.blogapp.dto.response.PostLikeResponseDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
@Tag(
        name = "PostLike APIs",
        description = "Operations related to blog PostLike"
)
public class PostLikeController {
    private final PostLikeService postLikeService;

    @Operation(summary = "Post like by UserID and PostID")
    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<PostLikeResponseDto> likePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postLikeService.likePost(userId, postId));
    }

    @Operation(summary = "unlike post by ID")
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {

        postLikeService.unLikePost(userId, postId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get post count by ID")
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(
            @PathVariable Long postId) {

        return ResponseEntity.ok(postLikeService.getLikeCountByPostId(postId));
    }

    @Operation(summary = "Get most liked post by ID")
    @GetMapping("/most-liked")
    public ResponseEntity<List<PostResponseDto>> getMostLikedPosts() {

        return ResponseEntity.ok(postLikeService.getMostLikedPosts());
    }

    @Operation(summary = "Check user liked a Post")
    @GetMapping("/check/{userId}/{postId}")
    public ResponseEntity<Boolean> hasUserLikedPost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(postLikeService.hasUserLikedPost(userId, postId));
    }

    @Operation(summary = "Get LikedPost by ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getLikedPostsByUser(
            @PathVariable Long userId){

        return ResponseEntity.ok(postLikeService.getLikedPostsByUser(userId));
    }

    @Operation(summary = "Get user who liked post")
    @GetMapping("/post/{postId}/users")
    public ResponseEntity<List<UserResponseDto>> getUsersWhoLikedPost(
            @PathVariable Long postId){

        return ResponseEntity.ok(postLikeService
                .getUsersWhoLikedPost(postId));
    }
}
