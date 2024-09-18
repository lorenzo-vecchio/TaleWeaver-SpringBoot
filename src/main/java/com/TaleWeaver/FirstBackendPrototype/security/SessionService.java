package com.TaleWeaver.FirstBackendPrototype.security;

import com.TaleWeaver.FirstBackendPrototype.models.Session;
import com.TaleWeaver.FirstBackendPrototype.models.User;
import com.TaleWeaver.FirstBackendPrototype.models.enums.SessionType;
import com.TaleWeaver.FirstBackendPrototype.repositories.SessionRepository;
import com.TaleWeaver.FirstBackendPrototype.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public Session createSession(String usernameOrEmail, SessionType sessionType) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Session session = new Session();
        session.setUser(user);
        session.setSessionType(sessionType);
        return sessionRepository.save(session);
    }

    public Optional<Session> validateSession(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public boolean isSessionExpired(Session session) {
        Date now = new Date();
        long sessionAgeInMillis = now.getTime() - session.getCreationDate().getTime();
        long sessionAgeInSeconds = sessionAgeInMillis / 1000;
        return sessionAgeInSeconds > session.getSessionType().getExpirationSeconds();
    }
}