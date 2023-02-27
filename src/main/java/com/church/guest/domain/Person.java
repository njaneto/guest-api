package com.church.guest.domain;

import lombok.*;

@Data
@Builder
public class Person {

    private String name;
    private String invitedBy;
    private Church church;
    private Birthday birthday;

}
