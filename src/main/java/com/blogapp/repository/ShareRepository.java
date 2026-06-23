package com.blogapp.repository;

import com.blogapp.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {


    List<Share> findByPostId(Long postId);

    List<Share> findByUserId(Long userId);

}
