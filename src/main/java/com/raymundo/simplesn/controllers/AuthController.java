package com.raymundo.simplesn.controllers;

import com.raymundo.simplesn.dto.UserLoginRequest;
import com.raymundo.simplesn.dto.UserRequest;
import com.raymundo.simplesn.exceptions.*;
import com.raymundo.simplesn.services.AuthService;
import com.raymundo.simplesn.validation.IsUUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public void register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult)
            throws UsernameAlreadyTakenException, ValidationException {
        validate(bindingResult);
        authService.register(userRequest.username(), userRequest.password(), userRequest.email(), userRequest.isAdmin());
    }

    @PostMapping(value = "/login")
    public void login(@Valid @RequestBody UserLoginRequest userRequest, BindingResult bindingResult,
                      HttpServletRequest request, HttpServletResponse response) throws ValidationException {
        validate(bindingResult);
        authService.login(userRequest.username(), userRequest.password(), request, response);
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws LogoutException {
        authService.logout(request, response);
    }

    @GetMapping(value = "/enable/{token}")
    public void enableAdmin(@PathVariable(value = "token") String token)
            throws UserNotFoundException, InvalidTokenException, TokenExpiredException, EnableNotAdminException {
        authService.enableAdmin(token);
    }

    @PostMapping(value = "/send_again/{userId}")
    public void sendEmailAgain(@Valid @IsUUID @PathVariable(value = "userId") String userId)
            throws UserNotFoundException, EnableNotAdminException {
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
