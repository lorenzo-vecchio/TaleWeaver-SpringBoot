package com.TaleWeaver.FirstBackendPrototype.controllers;

import com.TaleWeaver.FirstBackendPrototype.DTOs.LoginRequestDTO;
import com.TaleWeaver.FirstBackendPrototype.DTOs.SignupRequestDTO;
import com.TaleWeaver.FirstBackendPrototype.models.Session;
import com.TaleWeaver.FirstBackendPrototype.models.User;
import com.TaleWeaver.FirstBackendPrototype.models.enums.SessionType;
import com.TaleWeaver.FirstBackendPrototype.repositories.SessionRepository;
import com.TaleWeaver.FirstBackendPrototype.repositories.UserRepository;
import com.TaleWeaver.FirstBackendPrototype.utils.Constants;
import com.TaleWeaver.FirstBackendPrototype.utils.annotations.IsAuthenticated;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

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
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        try {
            User user = userRepository.findByUsername(loginRequestDTO.getUsername());
            boolean correctPassword = new BCryptPasswordEncoder().matches(loginRequestDTO.getPassword(), user.getPassword());
            if (correctPassword) {
                // create session
                Session session = new Session();
                session.setUser(user);
                session.setSessionType(SessionType.HOUR);
                sessionRepository.save(session);
                // create cookie
                Cookie cookie = new Cookie(Constants.COOKIE_NAME, session.getId().toString());
                cookie.setMaxAge(session.getSessionType().getExpirationSeconds());
                cookie.setPath("/");
                response.addCookie(cookie);
                // send the response
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    @IsAuthenticated
    public ResponseEntity<Void> logout(@CookieValue(Constants.COOKIE_NAME) String authKey, Principal principal, HttpServletResponse response) {
        // TODO: fix logout
        Session session = sessionRepository.findSessionById(UUID.fromString(authKey));
        Cookie cookie = new Cookie(Constants.COOKIE_NAME, session.getId().toString());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        sessionRepository.delete(session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
