package com.blogapp.service.impl;
import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.User;
import com.blogapp.exception.EmailAlreadyExistsException;
import com.blogapp.mapper.UserMapper;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.exception.UsernameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceImpl.class);
 // Helper Method
    @Override
    public User getUserEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));
    }

    @Override
    public UserResponseDto register(RegisterRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if(userRepository.existsByUsername(requestDto.getUsername())){
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        log.info("Registering user with email={}", requestDto.getEmail());

        User user = userMapper.toEntity(requestDto);

        user.setPassword(
                passwordEncoder.encode(requestDto.getPassword())
        );
        User savedUser = userRepository.save(user);

        log.info("Usser registered successfully. UserId={}", savedUser.getId());

        return userMapper.toDto(savedUser);
    }

    @Override
    public PageResponse<UserResponseDto> getAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage =
                userRepository.findAll(pageable);

        List<UserResponseDto> users =
                userMapper.toDtoList(userPage.getContent());

        return PageResponse.<UserResponseDto>builder()
                .content(users)
                .currentPage(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }


//    @Override
//    public List<UserResponseDto> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        // for loop
//        return userMapper.toDtoList(users);
//        //using java 8 stream
////        return users.stream()
////                .map(user -> UserResponseDto.builder()
////                        .id(user.getId())
////                        .displayName(user.getDisplayName())
////                        .username(user.getUsername())
////                        .email(user.getEmail())
////                        .profileImage(user.getProfileImage())
////                        .build())
////                .toList();
//    }

    @Cacheable(
            value = "users",
            key = "#id"
    )
    @Override
    public UserResponseDto getUserById(Long id) {
       User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User not found"));

    log.info("Fetching user. UserId={}", id);
        return userMapper.toDto(user);
    }


    @CacheEvict(
            value = "users",
            key = "#id"
    )
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("User not found"));
        log.info("Deleting user. UserId={}", id);

        userRepository.delete(user);

        log.info("User deleted successfully. UserId={}", id);
    }


    @Cacheable(
            value = "users",
            key = "#email"
    )
    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }


    @Cacheable(
            value = "users",
            key = "#username"
    )
    @Override
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    @CachePut(
            value = "users",
            key = "#id"
    )
    @Override
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto) {

        User user = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("User not found"));

        log.info("Updating user. UserId={}", id);

        user.setDisplayName(updateUserRequestDto.getDisplayName());
        user.setBio(updateUserRequestDto.getBio());
        user.setProfileImage(updateUserRequestDto.getProfileImage());
        User updateUser = userRepository.save(user);

        log.info("User updated successfully. UserId={}", id);

        return userMapper.toDto(updateUser);
    }


}
