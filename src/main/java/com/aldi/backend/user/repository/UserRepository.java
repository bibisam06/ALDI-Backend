package com.aldi.backend.user.repository;

import com.aldi.backend.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String Email);
    Users save(Users users);
}
