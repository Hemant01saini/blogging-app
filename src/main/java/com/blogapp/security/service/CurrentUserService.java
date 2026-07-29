package com.blogapp.security.service;

import com.blogapp.entity.User;
import com.blogapp.repository.UserRepository;
import com.blogapp.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        String email = SecurityUtils.getCurrentUsername();

        return userRepository.findByEmail(email)
                .orElseThrow(()->
                        new RuntimeException("User not found"));

    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
