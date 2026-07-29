package com.blogapp.repository;

import com.blogapp.entity.Post;
import com.blogapp.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Post, Long> {

    Page<Post> findByStatusAndDeletedAtIsNullOrderByCreatedAtDesc(
            PostStatus status,
            Pageable pageable
            );

    @Query("""
            SELECT p 
            FROM Post p
            WHERE p.status='PUBLISHED'
            AND p.deletedAt IS NULL
            ORDER BY p.viewCount DESC
            """)
    Page<Post> findTrendingPosts(Pageable pageable);

    // latest post return kregi following ky
    @Query("""
            SELECT p
            FROM Post p
            WHERE p.createdBy.id IN :followingIds
            AND p.deletedAt IS NULL
            ORDER BY p.createdAt DESC
            """)
    Page<Post> findFollowingPosts(
            @Param("followingIds")
            List<Long> followingIds,
            Pageable pageable
    );
}
