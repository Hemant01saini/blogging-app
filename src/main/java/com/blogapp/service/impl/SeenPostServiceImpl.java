package com.blogapp.service.impl;

import com.blogapp.entity.Post;
import com.blogapp.entity.SeenPost;
import com.blogapp.entity.User;
import com.blogapp.repository.SeenPostRepository;
import com.blogapp.service.SeenPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeenPostServiceImpl implements SeenPostService {

    private final SeenPostRepository seenPostRepository;

    @Override
    public List<Long> getSeenPostIds(Long userId) {

        return seenPostRepository.findByUserId(userId)
                .stream()
                .map(seenPost -> seenPost.getPost().getId())
                .toList();
    }

    @Override
    public void saveSeenPosts(Long userId, List<Long> postIds) {

        User user = new User();
        user.setId(userId);

        List<SeenPost> seenPosts = new ArrayList<>();

        for (Long postId : postIds) {

            if (!seenPostRepository.existsByUserIdAndPostId(userId, postId)) {

                Post post = new Post();
                post.setId(postId);

                SeenPost seenPost = SeenPost.builder()
                        .user(user)
                        .post(post)
                        .seenAt(LocalDateTime.now())
                        .build();

                seenPosts.add(seenPost);
            }
        }

        seenPostRepository.saveAll(seenPosts);
    }
}
