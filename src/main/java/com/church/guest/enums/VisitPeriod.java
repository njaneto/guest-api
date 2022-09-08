package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum VisitPeriod {
    MORNING(1, "MANHA"),
    AFTERNOON(2,"TARDE"),
    NIGHT(3,"NOITE")
    ;

    private int code;
    private String desc;

    public static VisitPeriod ofCode(Integer code){
        return Stream.of(values())
                .filter( visitPeriod -> Objects.equals(visitPeriod.code, code))
                .findAny()
                .orElse(null);
    }

}
