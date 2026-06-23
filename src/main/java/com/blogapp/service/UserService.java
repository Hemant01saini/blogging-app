package com.blogapp.service;

import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
import com.blogapp.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto register(RegisterRequestDto requestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    void deleteUser(Long id);

    UserResponseDto getUserByEmail(String email);

    UserResponseDto getUserByUsername(String username);

    UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto);
}
