package com.raymundo.simplesn.controllers;

import com.raymundo.simplesn.dto.UserRequest;
import com.raymundo.simplesn.dto.UserResponse;
import com.raymundo.simplesn.exceptions.NoPermissionException;
import com.raymundo.simplesn.exceptions.UserNotFoundException;
import com.raymundo.simplesn.exceptions.UsernameAlreadyTakenException;
import com.raymundo.simplesn.exceptions.ValidationException;
import com.raymundo.simplesn.services.UserService;
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
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UUIDValidator uuidValidator;

    @PostMapping(value = "/edit/{userId}")
    public void changeUserProfile(@PathVariable(value = "userId") String id,
                                  @Valid @RequestBody UserRequest userRequest,
                                  BindingResult bindingResult)
            throws UserNotFoundException, NoPermissionException, UsernameAlreadyTakenException, ValidationException {
        uuidValidator.validate(id, bindingResult);
        validate(bindingResult);

        userService.editUserProfile(UUID.fromString(id), userRequest.username(),
                userRequest.email(), userRequest.password(), userRequest.isAdmin());
    }

    @GetMapping(value = "/get/{userId}")
    public UserResponse getUserProfile(@PathVariable(value = "userId") String id)
            throws ValidationException, UserNotFoundException, NoPermissionException {
        BindingResult bindingResult = new MapBindingResult(Map.of(), "");
        uuidValidator.validate(id, bindingResult);
        validate(bindingResult);
        return userService.getUserProfile(UUID.fromString(id));
    }

    @GetMapping(value = "/get/all")
    public List<UserResponse> getAllUserProfiles() {
        return userService.getAllUserProfiles();
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
