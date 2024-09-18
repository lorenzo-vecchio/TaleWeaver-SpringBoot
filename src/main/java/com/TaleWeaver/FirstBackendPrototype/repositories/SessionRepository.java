package com.TaleWeaver.FirstBackendPrototype.repositories;

import com.TaleWeaver.FirstBackendPrototype.models.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.UUID;

public interface SessionRepository extends CrudRepository<Session, UUID> {
    Session findSessionById(UUID id);
    Session findSessionByCreationDate(Date creationDate);
}
