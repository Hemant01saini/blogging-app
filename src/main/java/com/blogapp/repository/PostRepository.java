package com.blogapp.repository;

import com.blogapp.dto.response.PostResponseDto;
import com.blogapp.entity.Post;
import com.blogapp.entity.User;
import com.blogapp.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

//    Ye "Spring" word wali saari posts la dega.

    List<Post> findByCreatedBy(User user);

    List<Post> findByStatus(PostStatus status);

    Page<Post> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable

    );

    Page<Post> findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
            PostStatus status,
            Pageable pageable
    );

    long countByCreatedBy(User user);

}
