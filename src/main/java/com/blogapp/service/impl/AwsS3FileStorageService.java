package com.blogapp.service.impl;

import com.blogapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3FileStorageService implements FileStorageService {

    private  final S3Client s3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;


    @Override
    public String uploadFile(MultipartFile file, String folder) {

        try{

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();

            String fileKey =
                    folder + "/" +fileName;

            PutObjectRequest request =
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .contentType(file.getContentType())
                            .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromBytes(file.getBytes())
            );

            String fileUrl = s3Client.utilities()
                    .getUrl(builder -> builder.bucket(bucketName).key(fileKey))
                    .toExternalForm();

            return fileKey;

        } catch (IOException e ){

            throw new RuntimeException("File upload failed", e);
        }


    }

    @Override
    public void deleteFile(String fileKey) {

        DeleteObjectRequest request =
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build();

        s3Client.deleteObject(request);
    }
}
