package com.TaleWeaver.FirstBackendPrototype.repositories;

import com.TaleWeaver.FirstBackendPrototype.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserById(UUID id);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameOrEmail(String username, String email);
}
