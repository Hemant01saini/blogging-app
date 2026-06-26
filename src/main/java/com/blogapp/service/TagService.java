package com.blogapp.service;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;
import com.blogapp.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {

    TagResponseDto createTag(
            TagRequestDto dto);

    List<TagResponseDto> getAllTags();

    TagResponseDto getTagByd(
            Long id);

    void deleteTag(Long id);

    Set<Tag> getTagsByIds(Set<Long> ids);

    TagResponseDto updateTag(
            Long id, TagRequestDto tagRequestDto
    );
}
