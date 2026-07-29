package com.blogapp.controller;

import com.blogapp.dto.response.*;
import com.blogapp.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(
        name = "Search APIs",
        description = "Operations related to blog Search"
)
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Get post search")
    @GetMapping("/posts")
    public ResponseEntity<PageResponse<PostResponseDto>> searchPosts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size

    ) {

        return ResponseEntity.ok(
                searchService.searchPosts(keyword, page, size)
        );
    }

    @Operation(summary = "Get search user")
    @GetMapping("/users")
    public ResponseEntity<PageResponse<UserResponseDto>> searchUsers(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(
                searchService.searchUsers(keyword,page,size));
    }

    @Operation(summary = "Get search category")
    @GetMapping("/categories")
    public ResponseEntity<PageResponse<CategoryResponseDto>> searchCategories(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                searchService.searchCategories(keyword,page,size));
    }

    @Operation(summary = "Get search Tag")
    @GetMapping("/tags")
    public ResponseEntity<PageResponse<TagResponseDto>> searchTags(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size

    ) {

        return ResponseEntity.ok(
                searchService.searchTags(keyword,page,size));
    }
}
