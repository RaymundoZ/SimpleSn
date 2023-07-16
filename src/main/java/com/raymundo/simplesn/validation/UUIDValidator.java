package com.raymundo.simplesn.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UUIDValidator implements Validator {

    private static final String MESSAGE = "You should provide a valid UUID";
    private static final String PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(String.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!supports(target.getClass())) return;
        String value = (String) target;
        Pattern pattern = Pattern.compile(PATTERN);
        if (!pattern.matcher(value).matches())
            errors.reject("", MESSAGE);
    }
}
