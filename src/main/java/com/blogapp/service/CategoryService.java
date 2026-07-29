package com.blogapp.service;

import com.blogapp.dto.request.CategoryRequestDto;
import com.blogapp.dto.response.CategoryResponseDto;
import com.blogapp.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(
            CategoryRequestDto dto);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategoryById(
            Long id);

    void deleteCategory(Long id);

    CategoryResponseDto updateCategory(
            Long id, CategoryRequestDto categoryRequestDto
    );


    Category getCategoryEntityById(Long id);
}
