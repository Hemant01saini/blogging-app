package com.blogapp.service;

import java.util.List;

public interface SeenPostService {

    List<Long> getSeenPostIds(Long userId);

    void saveSeenPosts(Long userId,
                       List<Long> postIds);
}
