package com.blogapp.service;

import com.blogapp.dto.response.FeedResponseDto;
import com.blogapp.dto.response.PageResponse;

public interface FeedService {

    PageResponse<FeedResponseDto> getFeed(int page, int size);

}
