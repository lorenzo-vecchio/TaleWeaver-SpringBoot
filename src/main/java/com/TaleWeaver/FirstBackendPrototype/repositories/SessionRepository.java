package com.TaleWeaver.FirstBackendPrototype.repositories;

import com.TaleWeaver.FirstBackendPrototype.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Session findSessionById(UUID id);
    Session findSessionByCreationDate(Date creationDate);
}
