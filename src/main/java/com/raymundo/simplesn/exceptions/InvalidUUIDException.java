package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUUIDException extends BaseException {

    private static final String MESSAGE = "The '%s' is invalid uuid";

    public InvalidUUIDException(String uuid) {
        super(MESSAGE, HttpStatus.FORBIDDEN, uuid);
    }
}
