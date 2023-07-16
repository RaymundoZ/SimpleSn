package com.raymundo.simplesn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.HashSet;
import java.util.Set;

public class ValidationException extends Exception {

    private final Set<String> messages;
    private final HttpStatusCode status;

    public ValidationException() {
        messages = new HashSet<>();
        status = HttpStatus.FORBIDDEN;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public Set<String> getMessages() {
        return messages;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
