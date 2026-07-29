package com.blogapp.repository;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.repository.projection.CommentCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


//    List<Comment> findByPostId(Long postId);
//
//    List<Comment> findByUserId(Long userId);

    Page<Comment> findByPostIdOrderByCreatedAtDesc(
            Long postId,
            Pageable pageable
    );

    long countByPostId(Long postId);

    Long countByPost(Post post);

    @Query("""
            SELECT 
            c.post.id AS postId,
            COUNT(c.id) AS commentsCount
            FROM Comment c
            WHERE c.post.id IN :postIds
            GROUP BY c.post.id
            """)
    List<CommentCount> countCommentsByPostIds(List<Long> postIds);

    @Query("""
            SELECT COUNT(c)
            FROM Comment c
            WHERE c.post.createdBy.id = :userId
            """)
    long countCommentsReceived(Long userId);

}
