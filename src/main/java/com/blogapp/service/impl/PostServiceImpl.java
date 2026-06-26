package com.blogapp.service.impl;
import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.request.UpdatePostRequestDto;
import com.blogapp.dto.response.CategoryResponseDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.Tag;
import com.blogapp.entity.User;
import com.blogapp.mapper.PostMapper;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.CategoryService;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final PostMapper postMapper;




    @Override
    public PostResponseDto createPost(CreatePostRequestDto postRequestDto) {

        User user =
                userService.getUserEntityById(postRequestDto.getCreatedById());

        Category category =
                categoryService.getCategoryEntityById(postRequestDto.getCategoryId());

        Set<Tag> tags =
                tagService.getTagsByIds(postRequestDto.getTagIds());


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

    @Override
    public PostResponseDto updatePost(Long id, UpdatePostRequestDto updatePostRequestDto) {

        Post post = postRepository.findById(id).orElseThrow(()->
                new RuntimeException("Post not found"));

        Category category = categoryService.getCategoryEntityById(
                updatePostRequestDto.getCategoryId());

        Set<Tag> tags = tagService.getTagsByIds(updatePostRequestDto.getTagIds());

        post.setTitle(updatePostRequestDto.getTitle());
        post.setContent(updatePostRequestDto.getContent());
        post.setStatus(updatePostRequestDto.getStatus());
        post.setCategory(category);
        post.setTags(tags);

        Post updatedPost = postRepository.save(post);
        return postMapper.toDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(()->
                new RuntimeException("Post not found"));

        postRepository.delete(post);
    }

    @Override
    public PostResponseDto getPostById(Long id) {

       Post post = postRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Post not found"));

        return postMapper.toDto(post);
    }

    @Override
    public Post getPostEntityById(Long id) {

        return postRepository.findById(id).orElseThrow(()->
                new RuntimeException("Post Not Found"));
    }
}
