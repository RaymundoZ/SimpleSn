package com.raymundo.simplesn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raymundo.simplesn.util.BaseDto;

import java.time.LocalTime;
import java.util.Set;

public record ExceptionResponse(

        Set<String> messages,

        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime timestamp
) implements BaseDto {

}
