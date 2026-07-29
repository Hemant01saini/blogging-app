package com.blogapp.mapper;

import com.blogapp.dto.request.CategoryRequestDto;
import com.blogapp.dto.response.CategoryResponseDto;
import com.blogapp.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public Category toEntity(
            CategoryRequestDto dto){

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public CategoryResponseDto toDto(
            Category category){

        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public List<CategoryResponseDto> toDtoList(
            List<Category> categories){

        List<CategoryResponseDto> dtos =
                new ArrayList<>();

        for (Category category : categories){
            dtos.add(toDto(category));
        }

        return dtos;
    }

}
