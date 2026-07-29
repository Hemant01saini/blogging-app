package com.blogapp.service.impl;
import com.blogapp.dto.response.MediaResponseDto;
import com.blogapp.entity.Media;
import com.blogapp.entity.User;
import com.blogapp.exception.InvalidFileException;
import com.blogapp.exception.MediaNotFoundException;
import com.blogapp.mapper.MediaMapper;
import com.blogapp.repository.MediaRepository;
import com.blogapp.service.FileStorageService;
import com.blogapp.service.MediaService;
import com.blogapp.service.UserService;
import com.blogapp.util.FileConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final UserService userService;
    private final MediaMapper mediaMapper;
    private final FileStorageService fileStorageService;

    private static final Logger log =
            LoggerFactory.getLogger(MediaServiceImpl.class);

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Override
    public MediaResponseDto uploadProfileImage(Long userId, MultipartFile file) {

        log.info("Uploading profile image for UserId={}", userId);

//        if (file.isEmpty()) {
//            throw new RuntimeException("Please select an image.");
//        }
//
//        String contentType = file.getContentType();
//
//        if (contentType == null ||
//                !FileConstants.ALLOWED_IMAGE_TYPES.contains(contentType)){
//
//            throw new RuntimeException(
//                    "Only JPG, JPEG and PNG images are allowed");
//        }
//
//        if (file.getSize() > FileConstants.MAX_FILE_SIZE){
//            throw new RuntimeException("File size should not exceed 5 MB");
//        }

        validateImage(file);


        User user = userService.getUserEntityById(userId);

        if (mediaRepository.existsByUserId(userId)) {
            throw new RuntimeException("Profile image already exists. Use update API.");
        }


//            String originalFileName =
//                    file.getOriginalFilename() != null
//                            ? file.getOriginalFilename()
//                            : "image";
//
//
//            //Ye duplicate filename problem solve karta hai.
//            String uniqueFileName =
//                    UUID.randomUUID() + "_" + originalFileName;
//
//            //Banega:
//            //
//            //uploads/profile
//            Path uploadPath =
//                    Paths.get(FileConstants.PROFILE_DIR);
//
////            Agar folder nahi hai to create kar dega.
//            if (!Files.exists(uploadPath)){
//                Files.createDirectories(uploadPath);
//            }
//
//            //Banega:
//            //
//            //uploads/profile/a1b2c3d4-profile.jpg
//            Path filePath =
//                    uploadPath.resolve(uniqueFileName);

            //Actual image disk par save ho jayegi.

            String fileKey =
                    fileStorageService.uploadFile(file, "profile");

            String fileUrl =
                    "https://" + bucketName + ".s3.amazonaws.com/" + fileKey;

            Media media = Media.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileKey(fileKey)
                    .filePath(fileUrl)
                    .user(user)
                    .build();
//                                                                    .build();
//            media.setFileKey(fileKey);
//            media.setFilePath(fileUrl);

//            Media media = Media.builder()
//                    .fileName(uniqueFileName)
//                    .fileType(file.getContentType())
//                    .filePath(filePath.toString())
//                    .user(user)
//                    .build();

            Media savedMedia = mediaRepository.save(media);

            log.info("Profile image uploaded successfully. UserId={}, Filekey={}",
                    userId,
                    savedMedia.getFileKey());

            return mediaMapper.toDto(savedMedia);

    }

    @Override
    public MediaResponseDto getProfileImage(Long userId) {

        Media media = mediaRepository
                .findByUserId(userId)
                .orElseThrow(()->
                        new MediaNotFoundException("Profile image not found"));
log.info("Fetching profile image. UserId={}", userId);

        return mediaMapper.toDto(media);
    }

    @Override
    public MediaResponseDto updateProfileImage(Long userId, MultipartFile file) {

        log.info("Updating profile image. UserId={}", userId);

        validateImage(file);

       Media media =  mediaRepository.findByUserId(userId).orElseThrow(()->
               new MediaNotFoundException("Profile image not found"));


                                if(media.getFileKey() != null && !media.getFileKey().isBlank()) {
                                    fileStorageService.deleteFile(media.getFileKey());
                                }

                                String fileKey = fileStorageService.uploadFile(file, "profile");

                                String fileUrl =
                                        "https://" + bucketName + ".s3.amazonaws.com/" + fileKey;

                                media.setFileName(file.getOriginalFilename());
                                media.setFileType(file.getContentType());
                                media.setFileKey(fileKey);
                                media.setFilePath(fileUrl);

                                Media updatedMedia = mediaRepository.save(media);

                                log.info("Profile image updated successfully. UserId={}", userId);

                                return mediaMapper.toDto(updatedMedia);
//                                String originalFileName = file.getOriginalFilename();
//
//                                String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
//
//                                Path uploadPath =
//                                    Paths.get(FileConstants.UPLOAD_DIR);

//                            if (!Files.exists(uploadPath)) {
//                                Files.createDirectories(uploadPath);
//                            }
//
//                                Path filePath =
//                                    uploadPath.resolve(uniqueFileName);
//
//                            Files.copy(
//                                    file.getInputStream(),
//                                    filePath,
//                                    StandardCopyOption.REPLACE_EXISTING
//                            );

//                            media.setFileName(uniqueFileName);
//                            media.setFileType(file.getContentType());
//                            media.setFilePath(filePath.toString());


//                            Media updatedMEdia =
//                                    mediaRepository.save(media);


//        return mediaMapper.toDto(updatedMEdia);

    }

    @Override
    public void deleteProfileImage(Long userId) {

        log.info("Deleting profile image. UserId={}", userId);

         Media media = mediaRepository.findByUserId(userId)
                 .orElseThrow(() ->
                         new MediaNotFoundException("Profile image not found"));

//         Path filePath = Paths.get(media.getFilePath());

         fileStorageService.deleteFile(media.getFileKey());

         mediaRepository.delete(media);

         log.info("Profile image deleted successfully. UserId={}", userId);

    }

    private void validateImage(MultipartFile file) {

        if (file.isEmpty()) {
            throw new InvalidFileException("Please select an image.");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !FileConstants.ALLOWED_IMAGE_TYPES.contains(contentType)){

            throw new InvalidFileException(
                    "Only JPG, JPEG and PNG images are allowed");
        }

        if (file.getSize() > FileConstants.MAX_FILE_SIZE){
            throw new InvalidFileException("File size should not exceed 5 MB");
        }
    }
}
