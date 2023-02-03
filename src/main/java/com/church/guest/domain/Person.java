package com.church.guest.domain;

import lombok.*;

@Data
@Builder
public class Person {

    public String name;
    public Church church;
    public Birthday birthday;

}
