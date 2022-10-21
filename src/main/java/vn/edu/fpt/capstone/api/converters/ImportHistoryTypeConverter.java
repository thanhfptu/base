package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.ImportHistoryType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImportHistoryTypeConverter implements AttributeConverter<ImportHistoryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ImportHistoryType type) {
        return type.getValue();
    }

    @Override
    public ImportHistoryType convertToEntityAttribute(Integer value) {
        return ImportHistoryType.of(value);
    }

}
