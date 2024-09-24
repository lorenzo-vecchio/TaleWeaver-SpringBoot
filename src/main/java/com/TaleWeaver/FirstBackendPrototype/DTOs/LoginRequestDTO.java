package com.TaleWeaver.FirstBackendPrototype.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 50)
    String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    String password;
}
