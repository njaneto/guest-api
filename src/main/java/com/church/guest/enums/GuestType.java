package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GuestType {

    VISITANTE(1),
    AVISO(2),
    ;

    private int code;

    public static GuestType ofCode(Integer code){
        return Stream.of(values())
                .filter( guestType -> Objects.equals(guestType.code, code))
                .findAny()
                .orElse(null);
    }
}
