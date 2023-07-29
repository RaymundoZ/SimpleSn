package com.raymundo.simplesn.controllers;

import com.raymundo.simplesn.dto.PublicationRequest;
import com.raymundo.simplesn.dto.PublicationResponse;
import com.raymundo.simplesn.exceptions.NoPermissionException;
import com.raymundo.simplesn.exceptions.PublicationNotFoundException;
import com.raymundo.simplesn.exceptions.UserNotFoundException;
import com.raymundo.simplesn.exceptions.ValidationException;
import com.raymundo.simplesn.services.PublicationService;
import com.raymundo.simplesn.validation.IsUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pub")
@RequiredArgsConstructor
@Validated
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping(value = "/add")
    public void createPublication(@Valid @RequestBody PublicationRequest publicationRequest, BindingResult bindingResult)
            throws ValidationException {
        validate(bindingResult);
        publicationService.createPublication(publicationRequest.text());
    }

    @PostMapping(value = "/edit/{publicationId}")
    public void editPublication(@Valid
                                @IsUUID
                                @PathVariable(value = "publicationId")
                                String id,

                                @Valid
                                @RequestBody
                                PublicationRequest publicationRequest,

                                BindingResult bindingResult)
            throws PublicationNotFoundException, ValidationException, NoPermissionException {
        validate(bindingResult);
        publicationService.editPublication(UUID.fromString(id), publicationRequest.text());
    }

    @DeleteMapping(value = "/remove/{publicationId}")
    public void deletePublication(@Valid @IsUUID @PathVariable(value = "publicationId") String id)
            throws PublicationNotFoundException, NoPermissionException {
        publicationService.deletePublication(UUID.fromString(id));
    }

    @GetMapping(value = "/get/all")
    public List<PublicationResponse> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @GetMapping(value = "/get/{userId}")
    public List<PublicationResponse> getAllPublicationsByUser(@Valid @IsUUID @PathVariable(value = "userId") String id)
            throws UserNotFoundException {
        return publicationService.getAllPublicationsByUser(UUID.fromString(id));
    }

    private void validate(BindingResult bindingResult) throws ValidationException {
        if (!bindingResult.hasErrors()) return;
        ValidationException exception = new ValidationException();
        bindingResult.getAllErrors().forEach(error -> {
            exception.addMessage(error.getDefaultMessage());
        });
        throw exception;
    }
}
