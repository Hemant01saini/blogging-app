package com.blogapp.mapper;

import com.blogapp.dto.response.FollowResponseDto;
import com.blogapp.entity.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {


    public FollowResponseDto toDto(
            Follow follow
    ){
        return FollowResponseDto.builder()
                .id(follow.getId())
                .followerId(
                        follow.getFollower().getId()
                )
                .followingId(
                        follow.getFollowing().getId()
                )
                .createdAt(
                        follow.getCreatedAt()
                )
                .build();
    }
}
