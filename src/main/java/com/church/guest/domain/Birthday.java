package com.church.guest.domain;

import com.church.guest.enums.BirthdayType;
import com.church.guest.repository.converters.BirthdayTypeConverter;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Convert;

@Data
@Builder
public class Birthday {

    @Convert(converter = BirthdayTypeConverter.class)
    private BirthdayType type;
    private String age;

}
