package com.raymundo.simplesn.util;

import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    @Value(value = "${user-initializer.enable:false}")
    private boolean isEnabled;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdmin() {
        if (!isEnabled) return;

        UserEntity user = new UserEntity();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("admin@gmail.com");
        user.setRole(Role.ADMIN_ENABLED);
        userRepository.save(user);
    }

    @PostConstruct
    public void createUser() {
        if (!isEnabled) return;

        UserEntity user = new UserEntity();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@gmail.com");
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
