package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class EnableNotAdminException extends BaseException {

    private static final String MESSAGE = "The '%s' user is not an admin or already enabled";

    public EnableNotAdminException(String username) {
        super(MESSAGE, HttpStatus.FORBIDDEN, username);
    }
}
