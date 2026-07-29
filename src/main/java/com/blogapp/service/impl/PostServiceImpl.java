package com.blogapp.service.impl;
import com.blogapp.dto.request.CreatePostRequestDto;
import com.blogapp.dto.request.UpdatePostRequestDto;
import com.blogapp.dto.response.CategoryResponseDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.Tag;
import com.blogapp.entity.User;
import com.blogapp.enums.PostStatus;
import com.blogapp.exception.PostNotFoundException;
import com.blogapp.mapper.PostMapper;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.CategoryService;
import com.blogapp.service.PostService;
import com.blogapp.service.TagService;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final PostMapper postMapper;


    private static final Logger log =
            LoggerFactory.getLogger(PostServiceImpl.class);



    @Override
    public PostResponseDto createPost(CreatePostRequestDto postRequestDto) {

        log.info("Creating new post");
        //"The Post entity has a ManyToOne relationship with User and Category.
        // Since these fields expect entity objects, I first fetch the corresponding
        // User and Category from the database using their IDs, and then set those entities
        // in the Post before saving."
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

        log.info("Post created successfully. PostId={}", savedPost.getId());

        return postMapper.toDto(savedPost);

    }

    @Override
    public PageResponse<PostResponseDto> getAllPosts(int page, int size) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Post> postPage =
                postRepository
                        .findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
                                PostStatus.PUBLISHED,
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


    @CachePut(
            value = "posts",
            key = "#id"
    )
    @Override
    public PostResponseDto updatePost(Long id, UpdatePostRequestDto updatePostRequestDto) {


        Post post = postRepository.findById(id).orElseThrow(()->
                new PostNotFoundException("Post not found"));

        Category category = categoryService.getCategoryEntityById(
                updatePostRequestDto.getCategoryId());

        Set<Tag> tags = tagService.getTagsByIds(updatePostRequestDto.getTagIds());

        log.info("Updating post. Id={}", id);

        post.setTitle(updatePostRequestDto.getTitle());
        post.setContent(updatePostRequestDto.getContent());
        post.setStatus(updatePostRequestDto.getStatus());
        post.setCategory(category);
        post.setTags(tags);

        Post updatedPost = postRepository.save(post);
        log.info("Post updated successfully. Id={}", id);
        return postMapper.toDto(updatedPost);
    }

    @CacheEvict(
            value = "posts",
            key = "#id"
    )
    @Override
    public void deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(()->
                new PostNotFoundException("Post not found"));

        log.info("Deleting post. Id={}", id);

        postRepository.delete(post);

        log.info("Post deleted successfully. Id={}", id);
    }

    @Cacheable(
            value = "posts",
            key = "#id"
    )
    @Override
    public PostResponseDto getPostById(Long id) {



       Post post = postRepository.findById(id)
                .orElseThrow(()->
                        new PostNotFoundException("Post not found"));

        log.info("Fetching post. Id={}", id);

        return postMapper.toDto(post);
    }

    @Override
    public Post getPostEntityById(Long id) {

        return postRepository.findById(id).orElseThrow(()->
                new PostNotFoundException("Post Not Found"));
    }

    @Override
    public long getPostsCount(Long userId) {

        User user = userService.getUserEntityById(userId);

        return postRepository.countByCreatedBy(user);
    }
}
