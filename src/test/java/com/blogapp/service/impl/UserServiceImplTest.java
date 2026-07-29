package com.blogapp.service.impl;

import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.entity.User;
import com.blogapp.exception.EmailAlreadyExistsException;
import com.blogapp.exception.UserNotFoundException;
import com.blogapp.exception.UsernameAlreadyExistsException;
import com.blogapp.mapper.UserMapper;
import com.blogapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Mockito Enable without this @Mock doesn't work
public class UserServiceImplTest {

    @Mock // create Fake Object
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks// it creates automatically new UserServiceImpl(userRepository,userMapper,passwordEncoder) we don't create constructor manually
    private UserServiceImpl userService; // Mock objects ko automatically class k andr inject krta hai

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequestDto request = new RegisterRequestDto();

        request.setDisplayName("Hemant");
        request.setUsername("hemant");
        request.setEmail("hemant@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setDisplayName("Hemant");
        user.setUsername("hemant");
        user.setEmail("hemant@gmail.com");
        user.setPassword("123456");

        UserResponseDto response = UserResponseDto.builder()
                .displayName("Hemant")
                .username("hemant")
                .email("hemant@gmail.com")
                .build();


        //Fake behaviour define karo
        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(false);

        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        when(userMapper.toDto(user))
                .thenReturn(response);

        UserResponseDto result =
                userService.register(request); // yahin actual method call hua.

        //Result check karo shi hai ya nhi
        assertEquals("Hemant",
                result.getDisplayName());

        assertEquals(
                "hemant@gmail.com",
                result.getEmail()
        );

        //Method call hui ya nahi, check karo.
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists()  {

        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("hemant@gmail.com");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        //JUnit method is code ko run kari if exception occurs PASS if not then FAIL
        assertThrows(
                EmailAlreadyExistsException.class,
                ()->userService.register(request)
        );

        verify(userRepository)
                .existsByEmail(request.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {

        RegisterRequestDto request = new RegisterRequestDto();
        request.setEmail("hemant@gmail.com");
        request.setUsername("hemant");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);
        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(true);

        assertThrows(
                UsernameAlreadyExistsException.class,
                ()-> userService.register(request)
        );

        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).existsByUsername(request.getUsername());
    }


    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                ()-> userService.getUserEntityById(1L)
        );

        verify(userRepository).findById(1L);
    }

}

