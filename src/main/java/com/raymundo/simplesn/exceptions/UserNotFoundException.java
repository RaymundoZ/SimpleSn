package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    private static final String MESSAGE = "User with '%s' id/name not found";

    public UserNotFoundException(String userIdOrName) {
        super(MESSAGE, HttpStatus.FORBIDDEN, userIdOrName);
    }
}
