package com.blogapp.mapper;

import com.blogapp.entity.Post;
import com.blogapp.dto.response.FeedResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedMapper {

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "authorName", source = "createdBy.displayName")
    @Mapping(target = "profileImage", source = "createdBy.profileImage")
    @Mapping(target = "categoryName", source = "category.name")

    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "commentsCount", ignore = true)
    FeedResponseDto toDto(Post post);

    List<FeedResponseDto> toDtoList(List<Post> posts);

}
