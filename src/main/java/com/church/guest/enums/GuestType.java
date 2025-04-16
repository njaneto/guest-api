package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GuestType {

    VISITOR_PASTO(7, "VISITANTE",0),
    VISITOR(1, "VISITANTE", 1),
    WARNING(2, "AVISO/RECADO", 2),
    BIRTHDAY(3, "ANIVERSÁRIO",4),
    BIRTHDAY_WEDDING(6, "ANIVERSÁRIO",5),
    PRAYER(4, "ORAÇÃO",6),
    PRESENTATION(5, "APRESENTAÇÃO",7),
    ;

    private final int code;
    private final String desc;
    private final int sort;


    public static GuestType ofCode(Integer code){
        return Stream.of(values())
                .filter( guestType -> Objects.equals(guestType.code, code))
                .findAny()
                .orElse(null);
    }
}
