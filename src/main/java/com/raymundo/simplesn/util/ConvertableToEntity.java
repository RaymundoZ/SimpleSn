package com.raymundo.simplesn.util;

public interface ConvertableToEntity<T extends BaseEntity> {

    T toEntity();
}
