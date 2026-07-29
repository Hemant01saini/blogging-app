package com.blogapp.controller;

import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.SavedPostResponseDto;
import com.blogapp.service.SavedPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-posts")
@RequiredArgsConstructor
@Tag(
        name = "SavedPost APIs",
        description = "Operations related to blog SavedPost"
)
public class SavedPostController {

    private final SavedPostService savedPostService;

    @Operation(summary = "Post saved by ID")
    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<SavedPostResponseDto> savePost(
            @PathVariable Long userId,
            @PathVariable Long postId ){

        return ResponseEntity.status(HttpStatus.CREATED).
                body(savedPostService.savePost(userId, postId));
    }

    @Operation(summary = "Delete saved post by ID")
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Void> unSavePost(
            @PathVariable Long userId,
            @PathVariable Long postId ){

        savedPostService.unSavePost(userId, postId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check is postSaved by ID")
    @GetMapping("/check/{userId}/{postId}")
    public ResponseEntity<Boolean> isPostSaved(
            @PathVariable Long userId,
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(savedPostService.isPostSaved(userId, postId));
    }

    @Operation(summary = "Get SavedPost by ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getSavedPostsByUser(
            @PathVariable Long userId
    )
    {
        return ResponseEntity.ok(savedPostService.getSavedPostsByUser(userId));
    }

}
