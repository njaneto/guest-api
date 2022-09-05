package com.church.guest.repository.converters;

import com.church.guest.enums.GuestType;
import com.church.guest.enums.VisitPeriod;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class VisitPeriodConverter  implements AttributeConverter<VisitPeriod, Integer>  {
    @Override
    public Integer convertToDatabaseColumn(VisitPeriod attribute) {
        return Optional.ofNullable(attribute)
                .map(VisitPeriod::getCode)
                .orElse(null);
    }

    @Override
    public VisitPeriod convertToEntityAttribute(Integer dbData) {
        return VisitPeriod.ofCode(dbData);
    }
}
