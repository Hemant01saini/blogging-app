package com.blogapp.service;

import com.blogapp.dto.request.CreateCommentRequestDto;
import com.blogapp.dto.request.UpdateCommentRequestDto;
import com.blogapp.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(CreateCommentRequestDto dto);

    List<CommentResponseDto> getCommentsByPostId(Long postId);

    CommentResponseDto getCommentById(Long id);

    CommentResponseDto updateComment(Long id, UpdateCommentRequestDto updateCommentRequestDto);

    long getCommentCountOnPost(Long postId);

    void deleteComment(Long id);
}
