package com.aldi.backend.user.service;

import com.aldi.backend.user.entity.Users;
import com.aldi.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Users save(Users user) {
        userRepository.save(user);
        return user;
    }
}
