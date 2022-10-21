package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;

import javax.persistence.AttributeConverter;

public class OJTInfoStatusConverter implements AttributeConverter<OJTInfoStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OJTInfoStatus type) {
        return type.getValue();
    }

    @Override
    public OJTInfoStatus convertToEntityAttribute(Integer value) {
        return OJTInfoStatus.of(value);
    }
}
