package com.blogapp.mapper;

import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public User toEntity(RegisterRequestDto dto)
    {
        return User.builder()
                .displayName(dto.getDisplayName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }


    public List<UserResponseDto> toDtoList(List<User> users){

        List<UserResponseDto> dtos = new ArrayList<>();

        for(User user: users){
            dtos.add(toDto(user));
        }
        return dtos;
    }



    public UserResponseDto toDto(User user){

        return UserResponseDto.builder()
                .id(user.getId())
                .displayName(user.getDisplayName())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .bio(user.getBio())
                .build();
    }



}
