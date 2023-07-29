package com.raymundo.simplesn.exceptions;

import com.raymundo.simplesn.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = PublicationNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlePublicationNotFoundException(PublicationNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = UsernameAlreadyTakenException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException e) {
        ExceptionResponse response = new ExceptionResponse(e.getMessages(), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = LogoutException.class)
    public ResponseEntity<ExceptionResponse> handleLogoutException(LogoutException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ExceptionResponse> handleTokenExpiredException(TokenExpiredException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTokenException(InvalidTokenException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = EnableNotAdminException.class)
    public ResponseEntity<ExceptionResponse> handleEnableNotAdminException(EnableNotAdminException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = NoPermissionException.class)
    public ResponseEntity<ExceptionResponse> handleNoPermissionException(NoPermissionException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(value = InvalidUUIDException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidUUIDException(InvalidUUIDException e) {
        ExceptionResponse response = new ExceptionResponse(Set.of(e.getMessage()), LocalTime.now());
        return ResponseEntity.status(e.getStatus()).body(response);
    }
}
