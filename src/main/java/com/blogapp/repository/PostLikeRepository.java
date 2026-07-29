package com.blogapp.repository;

import com.blogapp.entity.Post;
import com.blogapp.entity.PostLike;
import com.blogapp.repository.projection.PostLikeCount;
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

    Long countByPost(Post post);

    @Query("""
            SELECT 
            p1.post.id AS postId,
            COUNT(p1.id) AS likesCount
            FROM PostLike p1
            WHERE p1.post.id IN :postIds
            GROUP BY p1.post.id
            """)
    List<PostLikeCount> countLikesByPostIds(List<Long> postIds);

    @Query("""
            SELECT COUNT(p1)
            FROM PostLike p1
            WHERE p1.post.createdBy.id = :userId
            """)
    long countLikesReceived(Long userId);
}



