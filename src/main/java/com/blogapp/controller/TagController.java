package com.blogapp.controller;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;
import com.blogapp.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public TagResponseDto createTag(
            @Valid @RequestBody
            TagRequestDto dto){
        return tagService.createTag(dto);
    }

    @GetMapping
    public List<TagResponseDto> getAllTags(){

        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public TagResponseDto getTagById(
            @PathVariable Long id){

        return tagService.getTagByd(id);
    }

    @PutMapping("/{id}")
    public TagResponseDto updateTag(
            @PathVariable Long id,
            @RequestBody TagRequestDto dto
    ){
        return tagService.updateTag(id,dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(
            @PathVariable Long id){
        tagService.deleteTag(id);
    }
}
