package com.blogapp.security.service;

import com.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.blogapp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // CustomUserDetails db mein jaata hai  user find krne user mil gya toh convert in USerDetails
    // bcz spring only understand userDetail
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) // yha pe username ki jgh email kr diya valid h yeh bhi
            throws UsernameNotFoundException {

                User user = userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();
    }
}
