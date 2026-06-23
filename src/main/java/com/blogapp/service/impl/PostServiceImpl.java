package com.blogapp.service.impl;
import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.Tag;
import com.blogapp.entity.User;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.mapper.PostMapper;
import com.blogapp.repository.CategoryRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.repository.TagRepository;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostMapper postMapper;


    @Override
    public PostResponseDto createPost(CreatePostRequestDto postRequestDto) {


        User user = userRepository.findById(
                postRequestDto.getCreatedById())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

//        log.info("User Fetched "+user.getId());

        Category category = categoryRepository.findById(
                postRequestDto.getCategoryId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Category not found"));

        Set<Tag> tags = new HashSet<>(
                tagRepository.findAllById(
                        postRequestDto.getTagIds()));

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .status(postRequestDto.getStatus())
                .createdBy(user)
                .category(category)
                .tags(tags)
                .build();

        Post savedPost = postRepository.save(post);

        return postMapper.toDto(savedPost);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return postMapper.toDtoList(posts);
    }
}
