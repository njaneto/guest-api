package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GuestType {

    VISITOR(1, "VISITANTE"),
    WARNING(2, "AVISO"),
    BIRTHDAY(3, "ANIVERSÁRIO"),
    PRAYER(4, "ORAÇÃO"),
    PRESENTATION(5, "APRESENTAÇÃO")
    ;

    private int code;
    private String desc;

    public static GuestType ofCode(Integer code){
        return Stream.of(values())
                .filter( guestType -> Objects.equals(guestType.code, code))
                .findAny()
                .orElse(null);
    }
}
