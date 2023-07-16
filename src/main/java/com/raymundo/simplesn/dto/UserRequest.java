package com.raymundo.simplesn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raymundo.simplesn.util.BaseDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "The 'username' attribute should not be blank")
        @Size(max = 15, message = "The 'username' attribute should not be longer than 15 symbols")
        String username,

        @NotBlank(message = "The 'password' attribute should not be blank")
        @Size(min = 4, max = 20, message = "The 'password' attribute's size should be between 4 and 20 symbols")
        String password,

        @NotBlank(message = "The 'email' attribute should not be blank")
        @Size(max = 50, message = "The 'email' attribute's size should be less than 50 symbols")
        @Email(message = "The 'email' attribute should be valid")
        String email,

        @JsonProperty(value = "is_admin")
        @NotNull(message = "The 'is_admin' attribute should not be null")
        Boolean isAdmin
) implements BaseDto {
}
