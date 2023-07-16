package com.raymundo.simplesn.services;

import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.exceptions.*;
import com.raymundo.simplesn.repositories.UserRepository;
import com.raymundo.simplesn.util.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final UserService userService;

    public void register(String username, String password, String email, boolean isAdmin) throws UsernameAlreadyTakenException {
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isPresent())
            throw new UsernameAlreadyTakenException(username);

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(isAdmin ? Role.ADMIN_DISABLED : Role.USER);
        userRepository.save(user);

        if (!user.isEnabled())
            emailService.sendAdminEnableEmail(user);
    }

    public void login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .unauthenticated(username, password);
        WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
        webAuthenticationDetailsSource.buildDetails(request);
        Authentication authentication = authManager.authenticate(token);
        SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
        securityContext.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws LogoutException {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();
        if (authentication == null)
            throw new LogoutException();

        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    public void enableAdmin(String token)
            throws TokenExpiredException, InvalidTokenException, UserNotFoundException, EnableNotAdminException {
        UserEntity user = userService.getUserByUsername(jwtService.getUsername(token));
        if (user.isEnabled())
            throw new EnableNotAdminException(user.getUsername());

        userService.changeUserRole(user, Role.ADMIN_ENABLED);
    }

    public void sendEmailAgain(UUID userId) throws UserNotFoundException, EnableNotAdminException {
        UserEntity user = userService.getUserById(userId);
        if (user.isEnabled())
            throw new EnableNotAdminException(user.getUsername());

        emailService.sendAdminEnableEmail(user);
    }
}
