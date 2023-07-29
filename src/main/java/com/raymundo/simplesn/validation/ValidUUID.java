package com.raymundo.simplesn.validation;

import com.raymundo.simplesn.exceptions.InvalidUUIDException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.util.regex.Pattern;

public class ValidUUID implements ConstraintValidator<IsUUID, String> {

    private static final String PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";


    @SneakyThrows
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(PATTERN);
        if (!pattern.matcher(value).matches())
            throw new InvalidUUIDException(value);
        return true;
    }
}
