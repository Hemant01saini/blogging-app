package com.blogapp.controller;

import com.blogapp.dto.request.CreateCommentRequestDto;
import com.blogapp.dto.request.UpdateCommentRequestDto;
import com.blogapp.dto.response.CommentResponseDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(
        name = "Comment APIs",
        description = "Operations relted to comments"
)
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create comment")
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @Valid @RequestBody CreateCommentRequestDto dto
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(dto));
    }

    @Operation(summary = "get comment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable Long id){

        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @Operation(summary = "Get comments by post")
    @GetMapping("/post/{postId}")
    public ResponseEntity<PageResponse<CommentResponseDto>> getCommentsByPostId(

            @PathVariable Long postId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int sizee
    ){

        return ResponseEntity.ok(
                commentService.getCommentsByPostId(postId, page,sizee)
        );
    }

    @Operation(summary = "Update comment")
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @Valid
            @RequestBody UpdateCommentRequestDto dto)
    {
        return ResponseEntity.ok(commentService.updateComment(id,dto));
    }

    @Operation(summary = "Delete comment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get total comments on a post")
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getCommentCountOnPost(
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(commentService.getCommentCountOnPost(postId));
    }
}
