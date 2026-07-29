package com.blogapp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Schema(example = "hemant@gmail.com")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "Password@123")
    @NotBlank
    private String password;
}
