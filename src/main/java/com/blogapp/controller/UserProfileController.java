package com.blogapp.controller;

import com.blogapp.dto.response.UserProfileResponseDto;
import com.blogapp.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponseDto> getProfile(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }
}
