package com.raymundo.simplesn.controllers;

import com.raymundo.simplesn.dto.UserRequest;
import com.raymundo.simplesn.exceptions.*;
import com.raymundo.simplesn.services.AuthService;
import com.raymundo.simplesn.validation.UUIDValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UUIDValidator uuidValidator;

    @PostMapping(value = "/register")
    public void register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult)
            throws UsernameAlreadyTakenException, ValidationException {
        validate(bindingResult);
        authService.register(userRequest.username(), userRequest.password(), userRequest.email(), userRequest.isAdmin());
    }

    @PostMapping(value = "/login")
    public void login(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request,
                      HttpServletResponse response, BindingResult bindingResult) throws ValidationException {
        validate(bindingResult);
        authService.login(userRequest.username(), userRequest.password(), request, response);
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws LogoutException {
        authService.logout(request, response);
    }

    @PostMapping(value = "/enable/{token}")
    public void enableAdmin(@PathVariable(value = "token") String token)
            throws UserNotFoundException, InvalidTokenException, TokenExpiredException, EnableNotAdminException {
        authService.enableAdmin(token);
    }

    @PostMapping(value = "/send_again/{userId}")
    public void sendEmailAgain(@PathVariable(value = "userId") String userId)
            throws UserNotFoundException, EnableNotAdminException, ValidationException {
        BindingResult bindingResult = new MapBindingResult(Map.of(), "");
        uuidValidator.validate(userId, bindingResult);
        validate(bindingResult);
        authService.sendEmailAgain(UUID.fromString(userId));
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
