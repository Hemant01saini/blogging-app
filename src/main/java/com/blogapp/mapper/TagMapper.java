package com.blogapp.mapper;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;
import com.blogapp.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagMapper {

    public Tag toEntity(TagRequestDto dto){
        return Tag.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .build();
    }

    public TagResponseDto toDto(Tag tag){

        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }

    public List<TagResponseDto> toDtoList(
            List<Tag> tags){

        List<TagResponseDto> dtos = new ArrayList<>();

        for (Tag tag : tags){
            dtos.add(toDto(tag));
        }
        return dtos;
    }
}
