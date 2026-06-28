package com.blogapp.controller;

import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.SavedPostResponseDto;
import com.blogapp.service.SavedPostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
public class SavedPostController {

    private final SavedPostService savedPostService;

    @PostMapping("/{userId}/{postId}")
    public SavedPostResponseDto savePost(
            @PathVariable Long userId,
            @PathVariable Long postId ){

        return savedPostService.savePost(userId, postId);
    }

    @DeleteMapping("/{userId}/{postId}")
    public String unSavePost(
            @PathVariable Long userId,
            @PathVariable Long postId ){

        savedPostService.unSavePost(userId, postId);

        return "Post Unsaved Successfully";
    }

    @GetMapping("/check/{userId}/{postId}")
    public boolean isPostSaved(
            @PathVariable Long userId,
            @PathVariable Long postId
    ){
        return savedPostService.isPostSaved(userId, postId);
    }

    @GetMapping("/user/{userId}")
    public List<PostResponseDto> getSavedPostsByUser(
            @PathVariable Long userId
    )
    {
        return savedPostService.getSavedPostsByUser(userId);
    }

}
