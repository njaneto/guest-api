package com.church.guest.reception.entity;

import com.church.guest.reception.domain.Person;
import com.church.guest.reception.domain.Prayer;
import com.church.guest.reception.domain.Presentation;
import com.church.guest.reception.enums.GuestType;
import com.church.guest.reception.repository.converters.GuestTypeConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Convert;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Guest {

    @Id
    private String id;

    @CreatedDate()
    private LocalDateTime createdDate;

    @Convert(converter = GuestTypeConverter.class)
    private GuestType guestType;

    private Person person;
    private Prayer prayer;
    private Presentation presentation;
    private String message;
    private Boolean announced;

}
