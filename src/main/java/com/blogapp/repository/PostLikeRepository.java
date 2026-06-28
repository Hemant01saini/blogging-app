package com.blogapp.repository;

import com.blogapp.entity.Post;
import com.blogapp.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository  extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByLikedByIdAndPostId(Long userId, Long postId);

    long countByPostId(Long postId);

    boolean existsByLikedByIdAndPostId(Long userId, Long postId);

    @Query("""
            SELECT p1.post
            FROM PostLike p1
            GROUP BY p1.post
            ORDER BY COUNT(p1.id) DESC
            """)
    List<Post> findMostLikedPosts();

    List<PostLike> findByLikedById(Long userId);

    List<PostLike> findByPostId(Long postId);
 }



