package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BaseException {

    private static final String MESSAGE = "The '%s' token has expired";

    public TokenExpiredException(String token) {
        super(MESSAGE, HttpStatus.FORBIDDEN, token);
    }
}
