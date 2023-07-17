package com.raymundo.simplesn.controllers;

import com.raymundo.simplesn.dto.PublicationRequest;
import com.raymundo.simplesn.dto.PublicationResponse;
import com.raymundo.simplesn.exceptions.NoPermissionException;
import com.raymundo.simplesn.exceptions.PublicationNotFoundException;
import com.raymundo.simplesn.exceptions.UserNotFoundException;
import com.raymundo.simplesn.exceptions.ValidationException;
import com.raymundo.simplesn.services.PublicationService;
import com.raymundo.simplesn.validation.UUIDValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/pub")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    private final UUIDValidator uuidValidator;

    @PostMapping(value = "/add")
    public void createPublication(@Valid @RequestBody PublicationRequest publicationRequest, BindingResult bindingResult)
            throws ValidationException {
        validate(bindingResult);
        publicationService.createPublication(publicationRequest.text());
    }

    @PostMapping(value = "/edit/{publicationId}")
    public void editPublication(@PathVariable(value = "publicationId")
                                String id,

                                @Valid
                                @RequestBody
                                PublicationRequest publicationRequest,

                                BindingResult bindingResult)
            throws PublicationNotFoundException, ValidationException, NoPermissionException {
        uuidValidator.validate(id, bindingResult);
        validate(bindingResult);
        publicationService.editPublication(UUID.fromString(id), publicationRequest.text());
    }

    @DeleteMapping(value = "/remove/{publicationId}")
    public void deletePublication(@PathVariable(value = "publicationId") String id)
            throws PublicationNotFoundException, ValidationException, NoPermissionException {
        BindingResult bindingResult = new MapBindingResult(Map.of(), "");
        uuidValidator.validate(id, bindingResult);
        validate(bindingResult);
        publicationService.deletePublication(UUID.fromString(id));
    }

    @GetMapping(value = "/get/all")
    public List<PublicationResponse> getAllPublications() {
        return publicationService.getAllPublications();
    }

    @GetMapping(value = "/get/{userId}")
    public List<PublicationResponse> getAllPublicationsByUser(@PathVariable(value = "userId") String id)
            throws UserNotFoundException, ValidationException {
        BindingResult bindingResult = new MapBindingResult(Map.of(), "");
        uuidValidator.validate(id, bindingResult);
        validate(bindingResult);
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
