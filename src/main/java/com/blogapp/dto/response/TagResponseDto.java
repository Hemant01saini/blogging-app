package com.blogapp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponseDto {

    private Long id;
    private String name;
    private String color;
}
