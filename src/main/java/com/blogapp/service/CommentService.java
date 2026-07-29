package com.blogapp.service;

import com.blogapp.dto.request.CreateCommentRequestDto;
import com.blogapp.dto.request.UpdateCommentRequestDto;
import com.blogapp.dto.response.CommentResponseDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.entity.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface CommentService {

    CommentResponseDto createComment(CreateCommentRequestDto dto);

//    List<CommentResponseDto> getCommentsByPostId(Long postId);

    PageResponse<CommentResponseDto> getCommentsByPostId(
                Long postId,
                int page,
                int size
    );

    CommentResponseDto getCommentById(Long id);

    CommentResponseDto updateComment(Long id, UpdateCommentRequestDto updateCommentRequestDto);

    long getCommentCountOnPost(Long postId);

    void deleteComment(Long id);

    Long getCommentsCount(Post post);

    Map<Long ,Long> getCommentsCountMap(List<Long> postIds);

    long getTotalCommentsReceived(Long userId);
}

