package com.blogapp.service;

import com.blogapp.dto.request.LoginRequestDto;
import com.blogapp.dto.response.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto requestDto);
}
