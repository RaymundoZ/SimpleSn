package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class LogoutException extends BaseException {

    private static final String MESSAGE = "User is not logged in";

    public LogoutException() {
        super(MESSAGE, HttpStatus.FORBIDDEN, null);
    }
}
