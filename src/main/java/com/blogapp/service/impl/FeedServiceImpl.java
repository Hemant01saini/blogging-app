package com.blogapp.service.impl;

import com.blogapp.dto.response.FeedResponseDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.entity.Post;
import com.blogapp.mapper.FeedMapper;
import com.blogapp.security.service.CurrentUserService;
import com.blogapp.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedCandidateService feedCandidateService;

    private final SeenPostService seenPostService;

    private final FeedRankingService feedRankingService;

    private final FeedMapper feedMapper;

    private  final PostLikeService postLikeService;

    private final CommentService commentService;

    private final CurrentUserService currentUserService;

    private static final Logger log =
            LoggerFactory.getLogger(FeedServiceImpl.class);

    @Override
    public PageResponse<FeedResponseDto> getFeed(int page, int size) {



        Long userId = currentUserService.getCurrentUserId();

        log.info("Generating feed for UserId={}, Page={}, Size={}",
                userId,
                page,
                size);

        // tells how many records and which page want
        Pageable pageable = PageRequest.of(page, size);

        //Page<Post> return hogi
        // Candidate Generation

        Page<Post> postPage =
                feedCandidateService.getCandidatePosts(pageable);


        //current page ky post milte hai
        List<Post> posts = postPage.getContent();


        //Remove seen post
        List<Long> seenPostIds =
                seenPostService.getSeenPostIds(userId);

        List<Post> unseenPosts = posts.stream()
                .filter(post -> !seenPostIds.contains(post.getId()))
                .toList();



        //if unSeen posts available then show,
        // warna original candidate posts dikhao.
        if (!unseenPosts.isEmpty()) {
            posts =  new java.util.ArrayList<>(unseenPosts);
        }



        //Ranking
        posts = feedRankingService.rankPosts(
                posts);

        log.info("Feed ranking completed. TotalPosts={}", posts.size());

        //2. Convert Entity - > DTO
        List<FeedResponseDto> feed =
                feedMapper.toDtoList(posts);

        List<Long> postIds = posts.stream()
                .map(Post::getId)
                .toList();

        Map<Long, Long> likesMap =
                postLikeService.getLikesCountMap(postIds);

        Map<Long, Long> commentsMap =
                commentService.getCommentsCountMap(postIds);

        // Likes & Comments Count
        for (int i=0; i< posts.size(); i++) {

            Long postId = posts.get(i).getId();

            feed.get(i).setLikesCount(
                    likesMap.getOrDefault(postId, 0L)            );

            feed.get(i).setCommentsCount(
                    commentsMap.getOrDefault(postId, 0L)
            );
        }

        seenPostService.saveSeenPosts(userId, postIds);

        log.info("Seen posts saved for UserId={}, Count={}",
                userId,
                postIds.size());

        //3. Return Feed pageResponse me data + pagination info return
        return PageResponse.<FeedResponseDto>builder()
                .content(feed)
                .currentPage(postPage.getNumber())
                .pageSize(postPage.getSize())
                .totalElements(postPage.getTotalElements())
                .totalPages(postPage.getTotalPages())
                .last(postPage.isLast())
                .build();
    }
}