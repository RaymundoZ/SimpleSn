package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {

    private static final String MESSAGE = "The '%s' token is invalid";

    public InvalidTokenException(String token) {
        super(MESSAGE, HttpStatus.FORBIDDEN, token);
    }
}
