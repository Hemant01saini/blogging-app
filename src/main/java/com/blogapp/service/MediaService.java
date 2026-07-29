package com.blogapp.service;

import com.blogapp.dto.response.MediaResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    MediaResponseDto uploadProfileImage(Long userId, MultipartFile file);

    MediaResponseDto getProfileImage(Long userId);

    MediaResponseDto updateProfileImage(Long userId, MultipartFile file);

    void deleteProfileImage(Long userId);
}
