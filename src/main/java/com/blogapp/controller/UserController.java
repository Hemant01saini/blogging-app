package com.blogapp.controller;

import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
import com.blogapp.dto.response.PageResponse;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
        name = "User APIs",
        description = "Operations related to users"
)
public class UserController{

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
           @Valid @RequestBody RegisterRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(requestDto));
    }

    @Operation(summary = "Get all users")
   @GetMapping
    public ResponseEntity<PageResponse<UserResponseDto>> getAllUsers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size
    ){

        return ResponseEntity.ok(
                userService.getAllUsers(page, size)
        );
   }


   @Operation(summary = "Get user by ID")
   @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(
           @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id)
        );
   }

   @Operation(summary = "Delete user")
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id){

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
   }

   @Operation(summary = "Update user")
   @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
           @PathVariable Long id,
           @Valid
           @RequestBody UpdateUserRequestDto updateUserRequestDto
           )
   {
        return ResponseEntity.ok(userService.updateUser(id ,updateUserRequestDto)
        );

   }

   @Operation(summary = "Get user by email")
   @GetMapping("/email/{email}")
   public  ResponseEntity<UserResponseDto> getUserByEmail
           (@PathVariable String email){

        return ResponseEntity.ok(userService.getUserByEmail(email)
        );
   }

@Operation(summary = "Get user by username")
   @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(
            @PathVariable String username
   ){
        return ResponseEntity.ok(userService.getUserByUsername(username)
        );
   }

}
