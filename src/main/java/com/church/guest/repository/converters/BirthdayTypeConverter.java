package com.church.guest.repository.converters;

import com.church.guest.enums.BirthdayType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class BirthdayTypeConverter implements AttributeConverter<BirthdayType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BirthdayType attribute) {
        return Optional.ofNullable(attribute)
                .map(BirthdayType::getCode)
                .orElse(null);
    }

    @Override
    public BirthdayType convertToEntityAttribute(Integer dbData) {
        return BirthdayType.ofCode(dbData);
    }
}
