//package com.blogapp.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "media")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Media {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "image_url" , nullable = false)
//    private String url;
//
//    @Column(name = "image_path", nullable = false)
//    private String path;
//
//    @Column(name = "original_name")
//    private String originalName;
//
//    @Column(name = "stored_name")
//    private String storedName;
//
//    @Column(name = "mime_type")
//    private String mimeType;
//
//    @Column(name = "file_size")
//    private Long fileSize;
//
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//}
