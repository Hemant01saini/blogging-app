package com.blogapp.controller;

import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.request.UpdatePostRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
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

    @PostMapping()
    public PostResponseDto createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto
            ){
        return postService.createPost(createPostRequestDto);
    }

    @GetMapping
    public List<PostResponseDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponseDto getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }


    @PutMapping("/{id}")
    public PostResponseDto updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequestDto updatePostRequestDto){

        return postService.updatePost(id,updatePostRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deletePost(
            @PathVariable Long id){

        postService.deletePost(id);

        return "Post deleted Successfully";
    }

}
