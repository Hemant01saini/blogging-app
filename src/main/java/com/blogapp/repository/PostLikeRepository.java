package com.blogapp.repository;

import com.blogapp.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository  extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByLikedByIdAndPostId(Long userId, Long postId);

    long countByPostId(Long postId);

    boolean existsByLikedByIdAndPostId(Long userId, Long postId);
}
