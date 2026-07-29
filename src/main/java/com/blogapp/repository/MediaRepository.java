package com.blogapp.repository;


import com.blogapp.entity.Media;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

    Optional<Media> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Transactional
    void deleteByUserId(Long userId);
}
