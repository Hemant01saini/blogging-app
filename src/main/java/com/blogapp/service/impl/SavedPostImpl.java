package com.blogapp.service.impl;

import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.SavedPostResponseDto;
import com.blogapp.entity.Post;
import com.blogapp.entity.SavedPost;
import com.blogapp.entity.User;
import com.blogapp.mapper.PostMapper;
import com.blogapp.mapper.SavedPostMapper;
import com.blogapp.repository.SavedPostRepository;
import com.blogapp.service.PostService;
import com.blogapp.service.SavedPostService;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedPostImpl implements SavedPostService {

    private final SavedPostRepository savedPostRepository;

    private final SavedPostMapper savedPostMapper;

    private final UserService userService;

    private final PostService postService;

    private final PostMapper postMapper;


    @Override
    public SavedPostResponseDto savePost(Long userId, Long postId) {

        if (savedPostRepository.existsByUserIdAndPostId(userId, postId)){
            throw new RuntimeException("Post already saved");
        }

        User user = userService.getUserEntityById(
                userId);

        Post post = postService.getPostEntityById(
                postId);

        SavedPost savedPost =
                SavedPost.builder()
                .user(user)
                .post(post)
                .build();

        SavedPost saved =
                savedPostRepository.save(savedPost);

        return savedPostMapper.toDto(saved);
    }

    @Override
    public void unSavePost(Long userId, Long postId) {

        SavedPost savedPost = savedPostRepository
                .findByUserIdAndPostId(
                        userId, postId)
                .orElseThrow(()->
                        new RuntimeException("Saved post not found"));

        savedPostRepository.delete(savedPost);
    }

    @Override
    public boolean isPostSaved(Long userId, Long postId) {
        return savedPostRepository
                .existsByUserIdAndPostId(
                        userId, postId);
    }

    @Override
    public List<PostResponseDto> getSavedPostsByUser(Long userId) {

        List<SavedPost> savedPosts =
                savedPostRepository
                        .findByUserId(userId);

        List<PostResponseDto> posts =
                new ArrayList<>();

        for (SavedPost savedPost
                : savedPosts){

            posts.add(
                    postMapper.toDto(
                            savedPost.getPost()
                    )
            );
        }

        return posts;
    }
}
