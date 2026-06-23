package com.blogapp.controller;

import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public PostResponseDto createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto
            ){
        return postService.createPost(createPostRequestDto);
    }


}
