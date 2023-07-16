package com.raymundo.simplesn.util;

import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.exceptions.UserNotFoundException;
import com.raymundo.simplesn.exceptions.UsernameAlreadyTakenException;
import com.raymundo.simplesn.services.AuthService;
import com.raymundo.simplesn.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializer {

    private final AuthService authService;
    private final UserService userService;

    @PostConstruct
    public void createAdmin() throws UsernameAlreadyTakenException, UserNotFoundException {
        authService.register("admin", "admin", "admin@gmail.com", true);
        UserEntity user = userService.getUserByUsername("admin");
        userService.changeUserRole(user, Role.ADMIN_ENABLED);
    }

    @PostConstruct
    public void createUser() throws UsernameAlreadyTakenException {
        authService.register("user", "user", "user@gmail.com", false);
    }
}
