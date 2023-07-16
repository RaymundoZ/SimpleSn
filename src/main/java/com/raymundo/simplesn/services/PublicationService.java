package com.raymundo.simplesn.services;

import com.raymundo.simplesn.dto.PublicationResponse;
import com.raymundo.simplesn.entities.PublicationEntity;
import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.exceptions.NoPermissionException;
import com.raymundo.simplesn.exceptions.PublicationNotFoundException;
import com.raymundo.simplesn.exceptions.UserNotFoundException;
import com.raymundo.simplesn.repositories.PublicationRepository;
import com.raymundo.simplesn.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final UserService userService;

    public void createPublication(String text) {
        PublicationEntity publication = new PublicationEntity();
        publication.setText(text);
        publication.setCreationDate(new Date());
        publication.setUser(userService.getCurrentUser());
        publicationRepository.save(publication);
    }

    public void editPublication(UUID publicationId, String text) throws PublicationNotFoundException, NoPermissionException {
        PublicationEntity publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(publicationId));

        checkPermission(publication);

        publication.setText(text);
        publicationRepository.save(publication);
    }

    public void deletePublication(UUID publicationId) throws PublicationNotFoundException, NoPermissionException {
        PublicationEntity publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(publicationId));

        checkPermission(publication);

        publicationRepository.delete(publication);
    }

    public List<PublicationResponse> getAllPublications() {
        return publicationRepository.findAllByOrderByCreationDateAsc()
                .stream().map(PublicationEntity::toDto).toList();
    }

    public List<PublicationResponse> getAllPublicationsByUser(UUID userId) throws UserNotFoundException {
        UserEntity user = userService.getUserById(userId);
        return publicationRepository.findAllByUserOrderByCreationDateAsc(user)
                .stream().map(PublicationEntity::toDto).toList();
    }

    private void checkPermission(PublicationEntity publication) throws NoPermissionException {
        UserEntity user = userService.getUserByPublication(publication);
        UserEntity currentUser = userService.getCurrentUser();
        if (!currentUser.getRole().equals(Role.ADMIN_ENABLED) && !currentUser.getId().equals(user.getId()))
            throw new NoPermissionException();
    }
}
