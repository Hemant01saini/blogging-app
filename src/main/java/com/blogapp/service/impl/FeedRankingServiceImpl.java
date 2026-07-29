package com.blogapp.service.impl;

import com.blogapp.entity.Post;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.PostLikeRepository;
import com.blogapp.service.FeedRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class FeedRankingServiceImpl
        implements FeedRankingService {

    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    private static final int LIKE_WEIGHT = 5;
    private static final int COMMENT_WEIGHT = 3;
    private static final int VIEW_WEIGHT = 1;

    @Override
    public List<Post> rankPosts(List<Post> posts) {

        List<Post> rankedPosts =  new java.util.ArrayList<>(posts);

        rankedPosts.sort(
                Comparator
                        .comparingLong(this::calculateScore)
                        .reversed()
        );

        return rankedPosts;
    }



    private long calculateScore(Post post) {

        long likes =
                postLikeRepository.countByPost(post);

        long comments =
                commentRepository.countByPost(post);

        long views =
                post.getViewCount() == null
                ? 0
                : post.getViewCount();

        long recencyScore = calculateRecencyScore(post);

        return
                (likes * LIKE_WEIGHT)
                 + (comments * COMMENT_WEIGHT )
                + (views * VIEW_WEIGHT)
                + recencyScore;
    }
    private long calculateRecencyScore(Post post) {

        long hours =
                Duration.between(
                        post.getCreatedAt(),
                        LocalDateTime.now()
                ).toHours();

        if (hours <= 24) {
            return 100;
        }

        if (hours <= 72) {
            return 50;
        }

        if (hours <= 168) {
            return 20;
        }

        return 0;
    }
}
