package com.blogapp.service.impl;

import com.blogapp.dto.request.CategoryRequestDto;
import com.blogapp.dto.response.CategoryResponseDto;
import com.blogapp.entity.Category;
import com.blogapp.exception.CategoryNotFoundException;
import com.blogapp.mapper.CategoryMapper;
import com.blogapp.repository.CategoryRepository;
import com.blogapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //Helper Method
    @Override
    public Category getCategoryEntityById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found"));
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {

        if (categoryRepository.existsByName(
                dto.getName())){
            throw new CategoryNotFoundException("Category already exists");
        }

        Category category = categoryMapper.toEntity(dto);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categoryMapper.toDtoList(categories);
    }

    @Cacheable(
            value = "categories",
            key = "#id"
    )
    @Override
    public CategoryResponseDto getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()->
                        new CategoryNotFoundException("Category not found"));

        return categoryMapper.toDto(category);
    }

    @CacheEvict(
            value = "categories",
            key = "#id"
    )
    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }

    @CachePut(
            value = "categories",
            key = "#id"
    )
    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryRequestDto) {


        Category category = categoryRepository.findById(id).orElseThrow(()->
                new CategoryNotFoundException("Category not found"));

        category.setName(categoryRequestDto.getName());
        category.setDescription(categoryRequestDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(updatedCategory);
    }
}
