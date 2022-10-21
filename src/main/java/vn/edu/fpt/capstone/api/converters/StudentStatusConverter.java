package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.StudentStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StudentStatusConverter implements AttributeConverter<StudentStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StudentStatus status) {
        return status.getValue();
    }

    @Override
    public StudentStatus convertToEntityAttribute(Integer value) {
        return StudentStatus.of(value);
    }

}
