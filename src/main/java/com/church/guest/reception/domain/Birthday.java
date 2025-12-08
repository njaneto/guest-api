package com.church.guest.reception.domain;

import com.church.guest.reception.enums.BirthdayType;
import com.church.guest.reception.repository.converters.BirthdayTypeConverter;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.Convert;

@Data
@Builder
public class Birthday {

    @Convert(converter = BirthdayTypeConverter.class)
    private BirthdayType type;
    private String age;

}
