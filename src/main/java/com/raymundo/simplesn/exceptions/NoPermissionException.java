package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

public class NoPermissionException extends BaseException {

    private static final String MESSAGE = "You have no permission for this operation";

    public NoPermissionException() {
        super(MESSAGE, HttpStatus.FORBIDDEN, "");
    }
}
