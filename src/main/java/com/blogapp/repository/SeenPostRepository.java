package com.blogapp.repository;

import com.blogapp.entity.SeenPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface SeenPostRepository
        extends JpaRepository<SeenPost,Long>
{
    List<SeenPost> findByUserId(Long userId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SeenPost s WHERE s.seenAt < :date")
    void deleteOldSeenPosts(LocalDateTime date);
}

