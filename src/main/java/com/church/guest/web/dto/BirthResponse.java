package com.church.guest.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BirthResponse {

    private String id;
    private LocalDateTime createdDate;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate birthday;
    private int age;

}
