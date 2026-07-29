package com.blogapp.mapper;

import com.blogapp.dto.response.CommentResponseDto;
import com.blogapp.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public CommentResponseDto toDto(Comment comment){

        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())

                .author(comment.getUser() != null
                                ? comment.getUser().getDisplayName()
                                : null
                )
                .postId(comment.getPost() != null
                             ? comment.getPost().getId()
                                : null
                )
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public List<CommentResponseDto> toDtoList(List<Comment> comments){
        List<CommentResponseDto> dtos = new ArrayList<>();

        for (Comment comment : comments){
            dtos.add(toDto(comment));
        }
        return dtos;
    }

}
