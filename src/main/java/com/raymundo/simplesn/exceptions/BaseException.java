package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatusCode;

public class BaseException extends Exception {

    private final HttpStatusCode STATUS;

    public BaseException(String message, HttpStatusCode status, String arg) {
        super(String.format(message, arg));
        this.STATUS = status;
    }

    public HttpStatusCode getStatus() {
        return STATUS;
    }
}
