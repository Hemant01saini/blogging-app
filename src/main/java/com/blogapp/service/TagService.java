package com.blogapp.service;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;

import java.util.List;

public interface TagService {

    TagResponseDto createTag(
            TagRequestDto dto);

    List<TagResponseDto> getAllTags();

    TagResponseDto getTagByd(
            Long id);

    void deleteTag(Long id);
}
