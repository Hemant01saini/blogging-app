package com.blogapp.controller;

import com.blogapp.dto.response.PostLikeResponseDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{userId}/{postId}")
    public PostLikeResponseDto likePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {
        return postLikeService.likePost(userId, postId);
    }

    @DeleteMapping("/{userId}/{postId}")
    public String unlikePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {

        postLikeService.unLikePost(userId, postId);

        return "Post Unliked";
    }


    @GetMapping("/count/{postId}")
    public long getLikeCount(
            @PathVariable Long postId) {

        return postLikeService.getLikeCountByPostId(postId);
    }

    @GetMapping("/most-liked")
    public List<PostResponseDto> getMostLikedPosts() {

        return postLikeService.getMostLikedPosts();
    }

    @GetMapping("/check/{userId}/{postId}")
    public boolean hasUserLikedPost(
            @PathVariable Long userId,
            @PathVariable Long postId
    ) {
        return postLikeService.hasUserLikedPost(userId, postId);
    }

    @GetMapping("user/{userId}")
    public List<PostResponseDto> getLikedPostsByUser(
            @PathVariable Long userId){

        return postLikeService.getLikedPostsByUser(userId);
    }

    @GetMapping("/post/{postId}/users")
    public List<UserResponseDto> getUsersWhoLikedPost(
            @PathVariable Long postId){

        return postLikeService
                .getUsersWhoLikedPost(postId);
    }
}
