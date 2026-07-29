package com.blogapp.controller;

import com.blogapp.dto.request.CategoryRequestDto;
import com.blogapp.dto.response.CategoryResponseDto;
import com.blogapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@AllArgsConstructor
@Tag(
        name = "Category APIs",
        description = "Operations related to blog category"
)
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create Category")
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CategoryRequestDto dto
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(dto));
    }

    @Operation(summary = "Get all categories by ID")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(
            @PathVariable Long id){

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Admin has deleted the category")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id)
    {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Updated categories")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            @PathVariable Long id ,
            @Valid @RequestBody CategoryRequestDto dto
    ){
        return ResponseEntity.ok(categoryService.updateCategory(id,dto));
    }
}
