package com.TaleWeaver.FirstBackendPrototype.security;

import com.TaleWeaver.FirstBackendPrototype.models.Session;
import com.TaleWeaver.FirstBackendPrototype.models.enums.SessionType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final SessionService sessionService;

    public CustomAuthenticationFilter(SessionService sessionService, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.sessionService = sessionService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        String sessionType = request.getParameter("sessionType");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(usernameOrEmail, password);
        Authentication auth = getAuthenticationManager().authenticate(authRequest);

        if (auth.isAuthenticated()) {
            Session session = sessionService.createSession(auth.getName(), SessionType.valueOf(sessionType));
            response.setHeader("Auth-Token", session.getId().toString());
        }

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}