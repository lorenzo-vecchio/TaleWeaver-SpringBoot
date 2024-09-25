package com.TaleWeaver.FirstBackendPrototype.DTOs;

import com.TaleWeaver.FirstBackendPrototype.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.beans.Transient;

@Getter
public class SignupRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 50)
    String username;

    @NotBlank(message = "Invalid email format")
    @Email
    String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    String password;

    @Transient
    public User getUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptedPassword);
        return user;
    }

}
