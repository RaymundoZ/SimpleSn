package com.raymundo.simplesn.dto;

import com.raymundo.simplesn.util.BaseDto;
import com.raymundo.simplesn.util.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UserResponse(

        String id,
        String username,
        String email,

        @Enumerated(value = EnumType.STRING)
        Role role
) implements BaseDto {
}
