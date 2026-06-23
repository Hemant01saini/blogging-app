package com.blogapp.service.impl;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;
import com.blogapp.entity.Tag;
import com.blogapp.mapper.TagMapper;
import com.blogapp.repository.TagRepository;
import com.blogapp.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponseDto createTag(TagRequestDto dto) {

        if(tagRepository.existsByName(
                dto.getName())){

            throw new RuntimeException(
                    "Tag already exists");
        }

        Tag tag = tagMapper.toEntity(dto);

        Tag savedTag = tagRepository.save(tag);

        return tagMapper.toDto(savedTag);
    }

    @Override
    public List<TagResponseDto> getAllTags() {

        List<Tag> tags = tagRepository.findAll();

        return tagMapper.toDtoList(tags);
    }

    @Override
    public TagResponseDto getTagByd(Long id) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Tag not found"));

        return tagMapper.toDto(tag);
    }

    @Override
    public void deleteTag(Long id) {

        Tag tag = tagRepository.findById(id).orElseThrow(()->
                new RuntimeException("Tag not found"));

        tagRepository.delete(tag);
    }
}
