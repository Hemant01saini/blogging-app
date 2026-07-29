package com.blogapp.service;

import com.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FeedCandidateService {

    Page<Post> getCandidatePosts(Pageable pageable);
}
