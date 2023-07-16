package com.raymundo.simplesn.util;

public interface ConvertableToDto<T extends BaseDto> {

    T toDto();
}
