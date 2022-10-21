package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.StudentOJTStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StudentOJTStatusConverter implements AttributeConverter<StudentOJTStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StudentOJTStatus status) {
        return status.getValue();
    }

    @Override
    public StudentOJTStatus convertToEntityAttribute(Integer value) {
        return StudentOJTStatus.of(value);
    }

}
