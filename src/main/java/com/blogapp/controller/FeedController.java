package com.blogapp.controller;

import com.blogapp.dto.response.PageResponse;
import com.blogapp.dto.response.FeedResponseDto;
import com.blogapp.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
@Tag(
        name = "Feed APIs",
        description = "Operations related to personalized feed"
)
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "Get personalized feed")
    @GetMapping
    public ResponseEntity<PageResponse<FeedResponseDto>> getFeed(
            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(feedService.getFeed(page, size));
    }

}

