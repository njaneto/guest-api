package com.church.guest.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Events {

    @Id
    private String id;

    @CreatedDate()
    private LocalDateTime createdDate;

    private String eventName;
    private String departmentName;
    private LocalDateTime eventDate;

}
