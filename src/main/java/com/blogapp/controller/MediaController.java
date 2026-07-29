package com.blogapp.controller;

import com.blogapp.dto.response.MediaResponseDto;
import com.blogapp.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Tag(
        name = "Media APIs",
        description = "Operations related to blog Media"
)
public class MediaController {

    private final MediaService mediaService;

    @Operation(summary = "Upload profile by ID")
    @PostMapping("/profile/{userId}")
    public ResponseEntity<MediaResponseDto> uploadProfileImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mediaService.uploadProfileImage(userId, file));
    }

    @Operation(summary = "Get profile by ID")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<MediaResponseDto> getProfileImage(
            @PathVariable Long userId ) {

        return ResponseEntity.ok(mediaService.getProfileImage(userId));
    }

    @Operation(summary = "Updated profile by ID")
    @PutMapping("/profile/{userId}")
    public ResponseEntity<MediaResponseDto> updateProfileImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file ) {

        return ResponseEntity.ok(mediaService.updateProfileImage(userId, file));
    }

    @Operation(summary = "Delete profile by ID")
    @DeleteMapping("/profile/{userId}")
    public ResponseEntity<Void> deleteProfileImage(
            @PathVariable Long userId ) {

        mediaService.deleteProfileImage(userId);

        return ResponseEntity.noContent().build();
    }
}
