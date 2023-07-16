package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class PublicationNotFoundException extends BaseException {

    private static final String MESSAGE = "Publication with '%s' id not found";

    public PublicationNotFoundException(UUID publicationId) {
        super(MESSAGE, HttpStatus.FORBIDDEN, publicationId.toString());
    }
}
