package com.blogapp.mapper;

import com.blogapp.dto.response.MediaResponseDto;
import com.blogapp.entity.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

//    @Value("${media.base-url}") // Ye automatically application.yml se value utha leti hai.
//    private String mediaBaseUrl;

    public MediaResponseDto toDto(Media media) {

        return MediaResponseDto.builder()
                .id(media.getId())
                .fileName(media.getFileName())
                .fileType(media.getFileType())
                .imageUrl(media.getFilePath())
                .uploadedAt(media.getUploadedAt())
                .userId(media.getUser().getId())
                .build();
    }
}
