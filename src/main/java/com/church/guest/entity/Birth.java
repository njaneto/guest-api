package com.church.guest.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Birth {

    @Id
    private String id;

    @CreatedDate()
    private LocalDateTime createdDate;

    private String name;
    private LocalDate dateOfBirth;
    private int day;
    private int month;
    private int year;


}
