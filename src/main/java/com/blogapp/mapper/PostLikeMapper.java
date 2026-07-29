package com.blogapp.mapper;

import com.blogapp.dto.response.PostLikeResponseDto;
import com.blogapp.entity.PostLike;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostLikeMapper {

    public PostLikeResponseDto toDto(PostLike postLike){

        return PostLikeResponseDto.builder()
                .id(postLike.getId())
                .userId(postLike.getLikedBy().getId())
                .userName(postLike.getLikedBy().getDisplayName())
                .postId(postLike.getPost().getId())
                .createdAt(postLike.getCreatedAt())
                .build();
    }

    public List<PostLikeResponseDto> toDtoList(
            List<PostLike> postLikes){
        List<PostLikeResponseDto> dtos =
                new ArrayList<>();

        for (PostLike postLike : postLikes){
            dtos.add(toDto(postLike));
        }
        return dtos;
    }
}
