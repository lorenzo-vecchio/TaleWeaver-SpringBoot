package com.TaleWeaver.FirstBackendPrototype.controllers;

import com.TaleWeaver.FirstBackendPrototype.DTOs.LoginRequestDTO;
import com.TaleWeaver.FirstBackendPrototype.DTOs.SignupRequestDTO;
import com.TaleWeaver.FirstBackendPrototype.models.User;
import com.TaleWeaver.FirstBackendPrototype.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        try {
            User user = signupRequestDTO.getUser();
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            // login
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
