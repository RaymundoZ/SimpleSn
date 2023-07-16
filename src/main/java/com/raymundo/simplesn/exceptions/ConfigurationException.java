package com.raymundo.simplesn.exceptions;

public class ConfigurationException extends RuntimeException {

    private static final String MESSAGE = "The '%s' parameter was not specified";

    public ConfigurationException(String parameter) {
        super(String.format(MESSAGE, parameter));
    }
}
