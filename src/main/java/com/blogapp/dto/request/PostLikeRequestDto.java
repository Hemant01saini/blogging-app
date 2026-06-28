package com.blogapp.dto.request;

import lombok.Data;

@Data
public class PostLikeRequestDto {

    private Long userId;

    private Long postId;

}
