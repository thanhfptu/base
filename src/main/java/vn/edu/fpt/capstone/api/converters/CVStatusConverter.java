package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.CVStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CVStatusConverter implements AttributeConverter<CVStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CVStatus gender) {
        return gender.getValue();
    }

    @Override
    public CVStatus convertToEntityAttribute(Integer value) {
        return CVStatus.of(value);
    }

}
