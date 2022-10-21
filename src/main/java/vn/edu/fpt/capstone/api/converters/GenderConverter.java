package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        return gender.getValue();
    }

    @Override
    public Gender convertToEntityAttribute(Integer value) {
        return Gender.of(value);
    }

}
