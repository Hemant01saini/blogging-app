package com.blogapp.service.impl;

import com.blogapp.dto.request.TagRequestDto;
import com.blogapp.dto.response.TagResponseDto;
import com.blogapp.entity.Tag;
import com.blogapp.exception.TagNotFoundException;
import com.blogapp.mapper.TagMapper;
import com.blogapp.repository.TagRepository;
import com.blogapp.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    // Helper Method
    @Override
    public Set<Tag> getTagsByIds(Set<Long> ids){
        return new HashSet<>(tagRepository.findAllById(ids));
    }


    @Override
    public TagResponseDto createTag(TagRequestDto dto) {

        if(tagRepository.existsByName(
                dto.getName())){

            throw new TagNotFoundException(
                    "Tag already exists");
        }

        Tag tag = tagMapper.toEntity(dto);

        Tag savedTag = tagRepository.save(tag);

        return tagMapper.toDto(savedTag);
    }



    @CachePut(
            value = "tags",
            key = "#id"

    )
    @Override
    public TagResponseDto updateTag(Long id, TagRequestDto tagRequestDto) {

        Tag tag = tagRepository.findById(id).orElseThrow(()->
                new TagNotFoundException("Tag not found"));

        tag.setName((tagRequestDto.getName()));
        tag.setColor(tagRequestDto.getColor());

        Tag updatedTag = tagRepository.save(tag);

        return tagMapper.toDto(updatedTag);

    }




    @Override
    public List<TagResponseDto> getAllTags() {

        List<Tag> tags = tagRepository.findAll();

        return tagMapper.toDtoList(tags);
    }



    @Cacheable(
            value = "tags",
            key = "#id"

    )
    @Override
    public TagResponseDto getTagById(Long id) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(()->
                        new TagNotFoundException("Tag not found"));

        return tagMapper.toDto(tag);
    }

    @CacheEvict(
            value = "tags",
            key = "#id"

    )
    @Override
    public void deleteTag(Long id) {

        Tag tag = tagRepository.findById(id).orElseThrow(()->
                new TagNotFoundException("Tag not found"));

        tagRepository.delete(tag);
    }
}
