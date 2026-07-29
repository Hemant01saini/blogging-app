package com.blogapp.controller;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;
import com.blogapp.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Tag(
        name = "Tag APIs",
        description = "Operations related to blog Tag"
)
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Create Tag")
    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(
            @Valid @RequestBody
            TagRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTag(dto));
    }

    @Operation(summary = "Get all Tags")
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {

        return ResponseEntity.ok(tagService.getAllTags());
    }

    @Operation(summary = "Get tags by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagById(
            @PathVariable Long id) {

        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @Operation(summary = "Updated Tags")
    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updateTag(
            @PathVariable Long id,
            @Valid
            @RequestBody TagRequestDto dto
    ) {
        return ResponseEntity.ok(tagService.updateTag(id, dto));
    }

    @Operation(summary = "Deleted Tag")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @PathVariable Long id) {

            tagService.deleteTag(id);

       return ResponseEntity.noContent().build();
    }
}
