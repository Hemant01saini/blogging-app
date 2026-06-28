package com.blogapp.service.impl;

import com.blogapp.dto.response.PostLikeResponseDto;
import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.Post;
import com.blogapp.entity.PostLike;
import com.blogapp.entity.User;
import com.blogapp.mapper.PostLikeMapper;
import com.blogapp.mapper.PostMapper;
import com.blogapp.mapper.UserMapper;
import com.blogapp.repository.PostLikeRepository;
import com.blogapp.service.PostLikeService;
import com.blogapp.service.PostService;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    private final PostLikeMapper postLikeMapper;

    private final PostMapper postMapper;

    private final UserMapper userMapper;

    private final UserService userService;

    private final PostService postService;

    @Override
    public PostLikeResponseDto likePost(Long userId, Long postId) {

        if (postLikeRepository.existsByLikedByIdAndPostId(userId, postId)){
            throw new RuntimeException("Post already liked");
        }

        User user = userService.getUserEntityById(userId);

        Post post = postService.getPostEntityById(postId);

        PostLike postLike = PostLike.builder()
                .likedBy(user)
                .post(post)
                .build();

        PostLike savedLike = postLikeRepository.save(postLike);

        return postLikeMapper.toDto(savedLike);
    }

    @Override
    public void unLikePost(Long userId, Long postId) {

        PostLike postLike = postLikeRepository
                .findByLikedByIdAndPostId(userId, postId)
                .orElseThrow(()->
                        new RuntimeException("Like not found"));

        postLikeRepository.delete(postLike);
    }

    @Override
    public long getLikeCountByPostId(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }


    @Override
    public List<PostResponseDto> getMostLikedPosts() {

        List<Post> posts =
                postLikeRepository.findMostLikedPosts();

        return postMapper.toDtoList(posts);
    }


    @Override
    public boolean hasUserLikedPost(Long userId, Long postId) {
        return postLikeRepository
                .existsByLikedByIdAndPostId(userId, postId);
    }


    @Override
    public List<PostResponseDto> getLikedPostsByUser(Long userId) {

        List<PostLike> likes = postLikeRepository.findByLikedById(userId);

        List<PostResponseDto> posts = new ArrayList<>();

        for (PostLike like : likes){
            posts.add(
                    postMapper.toDto(
                            like.getPost()
                    )
            );
        }

        return posts;
    }



    @Override
    public List<UserResponseDto> getUsersWhoLikedPost(Long postId) {
        List<PostLike> likes =
                postLikeRepository.findByPostId(postId);

        List<UserResponseDto> users =
                new ArrayList<>();

        for(PostLike like : likes){

            users.add(
                    userMapper.toDto(
                            like.getLikedBy()
                    )
            );

    }
        return users;
}


}
