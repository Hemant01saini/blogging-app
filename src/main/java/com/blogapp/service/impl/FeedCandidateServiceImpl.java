package com.blogapp.service.impl;

import com.blogapp.entity.Post;
import com.blogapp.enums.PostStatus;
import com.blogapp.repository.FeedRepository;
import com.blogapp.security.service.CurrentUserService;
import com.blogapp.service.FeedCandidateService;
import com.blogapp.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class FeedCandidateServiceImpl
        implements FeedCandidateService {

    private final FeedRepository feedRepository;
    private final FollowService followService;
    private final CurrentUserService currentUserService;

    @Override
    public Page<Post> getCandidatePosts(Pageable pageable) {

        Long userId = currentUserService.getCurrentUserId();

        List<Long> followingIds =
                followService.getFollowingUserIds(userId);

        Page<Post> followingPosts = Page.empty(pageable);

        if (!followingIds.isEmpty()) {

            followingPosts =
                    feedRepository.findFollowingPosts(
                            followingIds,
                            pageable
                    );
        }

        Page<Post> latestPosts =
                feedRepository.findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
                        PostStatus.PUBLISHED,
                        pageable
                );

        List<Post> following =
                followingPosts.getContent();

        Page<Post> trendingPosts =
                feedRepository.findTrendingPosts(pageable);

        List<Post> latest = latestPosts.getContent();

        List<Post> trending = trendingPosts.getContent();

        List<Post> candidates = new ArrayList<>();

        candidates.addAll(following);
        candidates.addAll(latest);
        candidates.addAll(trending);

        Map<Long, Post> uniquePosts = new LinkedHashMap<>();

        for (Post post : candidates) {
            uniquePosts.put(post.getId(), post);
        }

        List<Post> finalCandidates =
                new ArrayList<>(uniquePosts.values());

        int start = (int) pageable.getOffset();

        if (start >= finalCandidates.size()) {
            return new PageImpl<>(
                    List.of(),
                    pageable,
                    finalCandidates.size()
            );
        }

        int end = Math.min(start + pageable.getPageSize(), finalCandidates.size());

        List<Post> pageContent = new ArrayList<>(finalCandidates.subList(start, end));

        return new PageImpl<>(
                pageContent,
                pageable,
                finalCandidates.size());
    }
}
