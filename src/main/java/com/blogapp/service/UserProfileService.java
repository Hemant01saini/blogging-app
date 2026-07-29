package com.blogapp.service;

import com.blogapp.dto.response.UserProfileResponseDto;

public interface UserProfileService {

    UserProfileResponseDto getUserProfile(Long userId);
}
