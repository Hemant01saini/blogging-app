package com.blogapp.mapper;

import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.entity.Post;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PostMapper {

    private final PostLikeRepository postLikeRepository;

    private final CommentRepository commentRepository;

    public PostResponseDto toDto(Post post){

        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())

                .author(post.getCreatedBy().getDisplayName())

                .category(
                        post.getCategory() != null
                        ? post.getCategory().getName()
                                : null
                )

                .likesCount(
                        postLikeRepository.countByPostId(post.getId())
                )
                .commentsCount(commentRepository.countByPostId(post.getId()))

                .createdAt(post.getCreatedAt())
                .build();
    }

    public List<PostResponseDto> toDtoList(List<Post> posts) {

        List<PostResponseDto> dtos = new ArrayList<>();

        for (Post post : posts){
            dtos.add(toDto(post));
        }
        return dtos;
    }

}
