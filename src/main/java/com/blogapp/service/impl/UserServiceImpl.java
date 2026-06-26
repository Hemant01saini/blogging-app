package com.blogapp.service.impl;
import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.User;
import com.blogapp.mapper.UserMapper;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

 // Helper Method
    @Override
    public User getUserEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @Override
    public UserResponseDto register(RegisterRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(userRepository.existsByUsername(requestDto.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        User user = userMapper.toEntity(requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }


    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        // for loop
        return userMapper.toDtoList(users);
        //using java 8 stream
//        return users.stream()
//                .map(user -> UserResponseDto.builder()
//                        .id(user.getId())
//                        .displayName(user.getDisplayName())
//                        .username(user.getUsername())
//                        .email(user.getEmail())
//                        .profileImage(user.getProfileImage())
//                        .build())
//                .toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
       User user = userRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }


    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->
                new RuntimeException("User not found"));
        userRepository.delete(user);
    }


    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }


    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }


    @Override
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto) {

        User user = userRepository.findById(id).orElseThrow(()->
                new RuntimeException("User not found"));

        user.setDisplayName(updateUserRequestDto.getDisplayName());
        user.setBio(updateUserRequestDto.getBio());
        user.setProfileImage(updateUserRequestDto.getProfileImage());
        User updateUser = userRepository.save(user);

        return userMapper.toDto(updateUser);
    }


}
