package com.blogapp.controller;

import com.blogapp.dto.request.LoginRequestDto;
import com.blogapp.dto.response.LoginResponseDto;
import com.blogapp.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication APIs",
        description = "User authentication and authorization"
)
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
           @Valid @RequestBody LoginRequestDto requestDto) {

        return ResponseEntity.ok(authService.login(requestDto));
    }

}
