package com.raymundo.simplesn.dto;

import com.raymundo.simplesn.util.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublicationRequest(

        @NotBlank(message = "The 'text' attribute should not be blank")
        @Size(max = 100, message = "The 'text' attribute should not be longer than 100 symbols")
        String text
) implements BaseDto {

}
