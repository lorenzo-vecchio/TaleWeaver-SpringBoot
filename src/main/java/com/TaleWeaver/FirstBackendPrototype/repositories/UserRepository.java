package com.TaleWeaver.FirstBackendPrototype.repositories;

import com.TaleWeaver.FirstBackendPrototype.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(UUID id);
    User findByUsername(String username);
    User findByEmail(String email);
}
