package com.raymundo.simplesn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raymundo.simplesn.util.BaseDto;

import java.util.Date;

public record PublicationResponse(

        String id,

        String text,
        String user,

        @JsonFormat(pattern = "dd.MM.yyyy, HH:mm:ss", timezone = "Europe/Moscow")
        Date creationDate
) implements BaseDto {
}
