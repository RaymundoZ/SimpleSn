package com.raymundo.simplesn.util;

import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdmin() {
        UserEntity user = new UserEntity();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("admin@gmail.com");
        user.setRole(Role.ADMIN_ENABLED);
        userRepository.save(user);
    }

    @PostConstruct
    public void createUser() {
        UserEntity user = new UserEntity();
        user.setUsername("user");
        user.setPassword("user");
        user.setEmail("user@gmail.com");
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
