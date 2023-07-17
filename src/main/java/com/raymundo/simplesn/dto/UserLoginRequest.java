package com.raymundo.simplesn.dto;

import com.raymundo.simplesn.util.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(

        @NotBlank(message = "The 'username' attribute should not be blank")
        @Size(max = 15, message = "The 'username' attribute should not be longer than 15 symbols")
        String username,

        @NotBlank(message = "The 'password' attribute should not be blank")
        @Size(min = 4, max = 20, message = "The 'password' attribute's size should be between 4 and 20 symbols")
        String password
) implements BaseDto {
}
