package com.blogapp.repository;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPostId(Long postId);

    List<Comment> findByUserId(Long userId);

    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    long countByPostId(Long postId);

}
