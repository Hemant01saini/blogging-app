package com.blogapp.service;

import com.blogapp.entity.Post;

import java.util.List;

public interface FeedRankingService {

    List<Post> rankPosts(List<Post> posts);
}
