package com.blogapp.service.impl;

import com.blogapp.dto.response.*;
import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.Tag;
import com.blogapp.entity.User;
import com.blogapp.mapper.CategoryMapper;
import com.blogapp.mapper.PostMapper;
import com.blogapp.mapper.TagMapper;
import com.blogapp.mapper.UserMapper;
import com.blogapp.repository.CategoryRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.repository.TagRepository;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    private final PostMapper postMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final UserMapper userMapper;

    @Override
    public PageResponse<PostResponseDto> searchPosts(String keyword,
                                             int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Post> postPage =
                postRepository.findByTitleContainingIgnoreCase(
                        keyword,
                        pageable
                );

        List<PostResponseDto> postDtos =
                postMapper.toDtoList(postPage.getContent());

        return PageResponse.<PostResponseDto>builder()
                .content(postDtos)
                .currentPage(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }

    @Override
    public PageResponse<CategoryResponseDto> searchCategories(String keyword,
                                                              int page, int size) {

        Pageable pageable =  PageRequest.of(page, size);

        Page<Category> categoryPage =
                categoryRepository.findByNameContainingIgnoreCase(
                        keyword,
                        pageable
                );


        List<CategoryResponseDto> categories =
                categoryMapper.toDtoList(categoryPage.getContent());

        return PageResponse.<CategoryResponseDto>builder()
                .content(categories)
                .currentPage(categoryPage.getNumber())
                .pageSize(categoryPage.getSize())
                .totalElements(categoryPage.getTotalElements())
                .totalPages(categoryPage.getTotalPages())
                .last(categoryPage.isLast())
                .build();
    }

    @Override
    public PageResponse<TagResponseDto> searchTags(String keyword,
                                                   int page,
                                                   int size) {
        Pageable pageable =  PageRequest.of(page, size);

        Page<Tag> tagPage =
                tagRepository.findByNameContainingIgnoreCase(keyword,pageable);

        List<TagResponseDto> tagDto =
                tagMapper.toDtoList(tagPage.getContent());

        return PageResponse.<TagResponseDto>builder()
                .content(tagDto)
                .currentPage(tagPage.getNumber())
                .pageSize(tagPage.getSize())
                .totalElements(tagPage.getTotalElements())
                .totalPages(tagPage.getTotalPages())
                .last(tagPage.isLast())
                .build();
    }

    @Override
    public PageResponse<UserResponseDto> searchUsers(String keyword,
                                                     int page,
                                                     int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage =
                userRepository.searchUsers(
                        keyword,
                        pageable
                );

        List<UserResponseDto> userDtos =
                userMapper.toDtoList(userPage.getContent());

        return PageResponse.<UserResponseDto>builder()
                .content(userDtos)
                .currentPage(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();

    }
}
