package com.church.guest.domain;

import com.church.guest.enums.GuestType;
import com.church.guest.repository.converters.GuestTypeConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import javax.persistence.Convert;
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

    private String name;

    private String message;

    @CreatedDate()
    private LocalDateTime createdDate;

    @Convert(converter = GuestTypeConverter.class)
    private GuestType guestType;

}
