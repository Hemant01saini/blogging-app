package com.blogapp.service.impl;

import com.blogapp.dto.request.LoginRequestDto;
import com.blogapp.dto.response.LoginResponseDto;
import com.blogapp.security.jwt.JwtService;
import com.blogapp.security.service.CustomUserDetailsService;
import com.blogapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    private static final Logger log =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {

        //Spring Security ka kaam hi authentication hai
        log.info("Login request received for email={}", requestDto.getEmail());
       authenticationManager.authenticate( // authentication manager khud db nhi dekta custom userdetail ko call krta hai
               new UsernamePasswordAuthenticationToken(
                       requestDto.getEmail(),
                       requestDto.getPassword()
               )
       );

       log.info("Authentication successful for email={}", requestDto.getEmail());

       // CustomUserDetails db mein jaata hai  user find krne user mil gya toh convert in USerDetails
        //    // bcz spring only understand userDetail

        //match krta hai password ko bcrypt se if match login
        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(
                        requestDto.getEmail()
                );

        // authmanager bolta hai USer genuine hai
        // auth service bolta hai isko token de do
        String token =
                jwtService.generateToken(userDetails);

        log.info("JWT token generated successfully for email={}", requestDto.getEmail());

        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
