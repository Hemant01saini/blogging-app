package com.blogapp.controller;

import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.request.UpdatePostRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(
        name = "Post APIs",
        description = "Operations related to blog posts"
)
public class PostController {

    private final PostService postService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),

    })

    @Operation(summary = "Create a new post")
    @PostMapping()
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(createPostRequestDto));
    }

    @Operation(summary = "Get all posts")
    @GetMapping
    public ResponseEntity<PageResponse<PostResponseDto>> getAllPosts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size
    )
    {
        return ResponseEntity.ok
                (postService.getAllPosts(page, size)
                );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post found"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<PostResponseDto> getPostById(

            @Parameter(description = "Post ID")
            @PathVariable Long id)
    {
        return ResponseEntity.ok(postService.getPostById(id));
    }

@Operation(summary = "Update post")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Post updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Post not found")
})
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(

            @Parameter(description = "Post ID")
            @PathVariable Long id,

            @Valid
            @RequestBody UpdatePostRequestDto updatePostRequestDto){

        return ResponseEntity.ok(postService.updatePost(id,updatePostRequestDto));
    }

    @Operation(summary = "Delete post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(

            @Parameter(description = "Post ID")
            @PathVariable Long id){

        postService.deletePost(id);

        return ResponseEntity.noContent().build();
    }

}
