package com.blogapp.controller;

import com.blogapp.dto.request.CreateCommentRequestDto;
import com.blogapp.dto.request.UpdateCommentRequestDto;
import com.blogapp.dto.response.CommentResponseDto;
import com.blogapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto createComment(
            @Valid @RequestBody CreateCommentRequestDto dto
            ){
        return commentService.createComment(dto);
    }

    @GetMapping("/{id}")
    public CommentResponseDto getCommentById(@PathVariable Long id){

        return commentService.getCommentById(id);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponseDto> getCommentsByPostId(
            @PathVariable Long postId
    ){
        return commentService.getCommentsByPostId(postId);
    }

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id,
                                            @RequestBody UpdateCommentRequestDto dto)
    {
        return commentService.updateComment(id,dto);
    }

    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);

        return "Comment deleted";
    }

    @GetMapping("/post/{postId}/count")
    public long getCommentCountOnPost(
            @PathVariable Long postId
    ){
        return commentService.getCommentCountOnPost(postId);
    }
}
