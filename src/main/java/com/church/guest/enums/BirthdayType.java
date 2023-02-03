package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum BirthdayType {
    WEDDING(1, "CASAMENTO"),
    LIFE(2, "VIDA"),
    ;

    private final int code;
    private final String desc;

    public static BirthdayType ofCode(Integer code){
        return Stream.of(values())
                .filter( guestType -> Objects.equals(guestType.code, code))
                .findAny()
                .orElse(null);
    }

}
