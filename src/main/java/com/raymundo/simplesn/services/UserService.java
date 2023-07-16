package com.raymundo.simplesn.services;

import com.raymundo.simplesn.dto.UserResponse;
import com.raymundo.simplesn.entities.PublicationEntity;
import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.exceptions.NoPermissionException;
import com.raymundo.simplesn.exceptions.UserNotFoundException;
import com.raymundo.simplesn.exceptions.UsernameAlreadyTakenException;
import com.raymundo.simplesn.repositories.UserRepository;
import com.raymundo.simplesn.util.Role;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;
    private final EmailService emailService;

    @Nullable
    public UserEntity getCurrentUser() {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();
        return authentication != null ? (UserEntity) authentication.getPrincipal() : null;
    }

    public UserEntity getUserById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    public UserEntity getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUserByPublication(PublicationEntity publication) {
        return userRepository.findByPublications(publication);
    }

    public void changeUserRole(UserEntity user, Role role) {
        user.setRole(role);
        userRepository.save(user);
    }

    public void editUserProfile(UUID userId, String username, String email, String password, boolean isAdmin)
            throws UsernameAlreadyTakenException, UserNotFoundException, NoPermissionException {
        checkPermission(userId);

        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isPresent())
            throw new UsernameAlreadyTakenException(username);

        UserEntity user = getUserById(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        if (isAdmin && user.getRole().equals(Role.USER)) {
            emailService.sendAdminEnableEmail(user);
            changeUserRole(user, Role.ADMIN_DISABLED);
        }
    }

    public UserResponse getUserProfile(UUID userId) throws UserNotFoundException, NoPermissionException {
        checkPermission(userId);

        UserEntity user = getUserById(userId);
        return user.toDto();
    }

    public List<UserResponse> getAllUserProfiles() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(UserEntity::toDto).toList();
    }

    private void checkPermission(UUID userId) throws NoPermissionException, UserNotFoundException {
        UserEntity user = getUserById(userId);
        UserEntity currentUser = getCurrentUser();
        if (!currentUser.getRole().equals(Role.ADMIN_ENABLED) && !currentUser.getId().equals(user.getId()))
            throw new NoPermissionException();
    }
}
