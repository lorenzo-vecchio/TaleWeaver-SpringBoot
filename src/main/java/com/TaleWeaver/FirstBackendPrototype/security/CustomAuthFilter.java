package com.TaleWeaver.FirstBackendPrototype.security;

import com.TaleWeaver.FirstBackendPrototype.models.Session;
import com.TaleWeaver.FirstBackendPrototype.models.User;
import com.TaleWeaver.FirstBackendPrototype.repositories.SessionRepository;
import com.TaleWeaver.FirstBackendPrototype.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomAuthFilter extends OncePerRequestFilter {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authKey = extractAuthKeyFromCookies(request.getCookies());

        if (authKey != null) {
            authenticateUser(authKey, request);
        }

        filterChain.doFilter(request, response);
    }

    private String extractAuthKeyFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("TaleWeaver".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void authenticateUser(String authKey, HttpServletRequest request) throws ServletException {
        try {
            UUID sessionId = UUID.fromString(authKey);
            Session session = sessionRepository.findSessionById(sessionId);

            if (session != null) {
                User user = session.getUser();
                if (user != null) {
                    UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getUsername());

                    // Create authentication object with details
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    // Set the details using WebAuthenticationDetailsSource
                    WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
                    authentication.setDetails(detailsSource.buildDetails(request));

                    // Set authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new ServletException("User not found for session ID: " + sessionId);
                }
            } else {
                throw new ServletException("No session found for ID: " + sessionId);
            }
        } catch (IllegalArgumentException e) {
            throw new ServletException("Invalid session ID format: " + authKey, e);
        }
    }
}