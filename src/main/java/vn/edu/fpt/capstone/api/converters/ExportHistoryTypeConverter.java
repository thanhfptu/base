package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.ExportHistoryType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ExportHistoryTypeConverter implements AttributeConverter<ExportHistoryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ExportHistoryType type) {
        return type.getValue();
    }

    @Override
    public ExportHistoryType convertToEntityAttribute(Integer value) {
        return ExportHistoryType.of(value);
    }

}
