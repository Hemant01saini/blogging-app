package com.blogapp.service.impl;

import com.blogapp.dto.request.CreateCommentRequestDto;
import com.blogapp.dto.request.UpdateCommentRequestDto;
import com.blogapp.dto.response.CommentResponseDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.entity.User;
import com.blogapp.exception.CommentNotFoundException;
import com.blogapp.mapper.CommentMapper;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.projection.CommentCount;
import com.blogapp.service.CommentService;
import com.blogapp.service.PostService;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final UserService userService;
    private final PostService postService;

    @Override
    public CommentResponseDto createComment(CreateCommentRequestDto dto) {

        User user = userService.getUserEntityById(dto.getUserId());

        Post post = postService.getPostEntityById(dto.getPostId());

        Comment comment = Comment.builder().
                content(dto.getContent())
                .user(user)
                .post(post).
                build();

        Comment savedComment = commentRepository.save(comment);
;
        return commentMapper.toDto(savedComment);
    }

//    @Override
//    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
//
//        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
//
//        return commentMapper.toDtoList(comments);
//    }

    @Override
    public PageResponse<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> commentPage =
                commentRepository.findByPostIdOrderByCreatedAtDesc(
                        postId,
                        pageable
                );
        List<CommentResponseDto> commentDtos =
                commentMapper.toDtoList(commentPage.getContent());

        return PageResponse.<CommentResponseDto>builder()
                .content(commentDtos)
                .currentPage(commentPage.getNumber())
                .pageSize(commentPage.getSize())
                .totalElements(commentPage.getTotalElements())
                .totalPages(commentPage.getTotalPages())
                .last(commentPage.isLast())
                .build();
    }

    @Override
    public CommentResponseDto getCommentById(Long id) {

       Comment comment = commentRepository.findById(id)
               .orElseThrow(()->
                       new CommentNotFoundException("Comment not found"));

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentResponseDto updateComment(Long id, UpdateCommentRequestDto updateCommentRequestDto) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->
                        new CommentNotFoundException("Comment not found"));

                comment.setContent(updateCommentRequestDto.getContent());

                Comment updatedComment = commentRepository.save(comment);

                 return commentMapper.toDto(updatedComment);
    }

    @Override
    public long getCommentCountOnPost(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Override
    public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->
                        new CommentNotFoundException("Comment not found"));

        commentRepository.delete(comment);
    }

    @Override
    public Long getCommentsCount(Post post) {
        return commentRepository.countByPost(post);
    }

    @Override
    public Map<Long, Long> getCommentsCountMap(List<Long> postIds) {
        return commentRepository
                .countCommentsByPostIds(postIds)
                .stream()
                .collect(Collectors.toMap(
                        CommentCount::getPostId,
                        CommentCount::getCommentsCount
                ));
    }

    @Override
    public long getTotalCommentsReceived(Long userId) {
        return commentRepository.countCommentsReceived(userId);
    }


}
