package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GuestType {

    VISITOR(1, "VISITANTE", 0),
    WARNING(2, "AVISO/RECADO", 1),
    BIRTHDAY(3, "ANIVERSÁRIO",2),
    BIRTHDAY_WEDDING(6, "ANIVERSÁRIO",3),
    PRAYER(4, "ORAÇÃO",4),
    PRESENTATION(5, "APRESENTAÇÃO",5),
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
