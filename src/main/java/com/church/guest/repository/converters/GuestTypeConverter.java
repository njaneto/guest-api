package com.church.guest.repository.converters;

import com.church.guest.enums.GuestType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class GuestTypeConverter implements AttributeConverter<GuestType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GuestType attribute) {
        return Optional.ofNullable(attribute)
                .map(GuestType::getCode)
                .orElse(null);
    }

    @Override
    public GuestType convertToEntityAttribute(Integer dbData) {
        return GuestType.ofCode(dbData);
    }
}
