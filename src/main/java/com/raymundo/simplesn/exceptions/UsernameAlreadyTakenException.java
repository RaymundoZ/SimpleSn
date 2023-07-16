package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends BaseException {

    private static final String MESSAGE = "Username '%s' is already taken";

    public UsernameAlreadyTakenException(String username) {
        super(MESSAGE, HttpStatus.FORBIDDEN, username);
    }
}
