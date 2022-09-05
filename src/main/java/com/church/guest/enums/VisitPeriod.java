package com.church.guest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum VisitPeriod {
    MORNING(1),
    AFTERNOON(2),
    NIGHT(3)
    ;

    private int code;

    public static VisitPeriod ofCode(Integer code){
        return Stream.of(values())
                .filter( visitPeriod -> Objects.equals(visitPeriod.code, code))
                .findAny()
                .orElse(null);
    }

}
