package com.church.guest.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Sector {

    @Id
    private String id;

    @CreatedDate()
    private LocalDateTime createdDate;

    private String value;
}
