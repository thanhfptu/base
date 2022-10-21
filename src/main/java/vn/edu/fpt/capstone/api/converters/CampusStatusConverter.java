package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.CampusStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CampusStatusConverter implements AttributeConverter<CampusStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CampusStatus status) {
        return status.getValue();
    }

    @Override
    public CampusStatus convertToEntityAttribute(Integer value) {
        return CampusStatus.of(value);
    }

}
