package com.raymundo.simplesn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raymundo.simplesn.util.BaseDto;

import java.util.Date;
import java.util.Set;

public record ExceptionResponse(

        Set<String> messages,

        @JsonFormat(pattern = "HH:mm:ss")
        Date timestamp
) implements BaseDto {

}
