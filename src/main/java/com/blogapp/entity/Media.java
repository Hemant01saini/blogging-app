package com.blogapp.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    private String filePath;

    @Column(nullable = false)
    private String fileKey;

    @UpdateTimestamp
    private LocalDateTime uploadedAt;

    @OneToOne
    @JoinColumn(name = "user_id")  // one user has only one profile image
    private User user;

}
