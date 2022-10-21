package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.EvaluationStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EvaluationStatusConverter implements AttributeConverter<EvaluationStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EvaluationStatus type) {
        return type.getValue();
    }

    @Override
    public EvaluationStatus convertToEntityAttribute(Integer value) {
        return EvaluationStatus.of(value);
    }
}
