package com.blogapp.controller;

import com.blogapp.dto.request.RegisterRequestDto;
import com.blogapp.dto.request.UpdateUserRequestDto;
import com.blogapp.dto.response.UserResponseDto;
import com.blogapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDto register(
           @Valid @RequestBody RegisterRequestDto requestDto
    ){
        return userService.register(requestDto);
    }

   @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
   }

   @GetMapping("/{id}")
    public UserResponseDto findById(
           @PathVariable Long id) {
        return userService.getUserById(id);
   }

   @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Long id){
        userService.deleteUser(id);
        return "User deleted successfully";
   }

   @PutMapping("/{id}")
    public UserResponseDto updateUser(
           @PathVariable Long id,
           @RequestBody UpdateUserRequestDto updateUserRequestDto
           ) {
        return userService.updateUser(id ,updateUserRequestDto);

   }

   @GetMapping("/email/{email}")
   public UserResponseDto getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
   }


   @GetMapping("/username/{username}")
    public UserResponseDto getUserByUsername(
            @PathVariable String username
   ){
        return userService.getUserByUsername(username);
   }

}
