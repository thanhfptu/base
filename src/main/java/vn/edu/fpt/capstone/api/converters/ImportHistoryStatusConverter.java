package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImportHistoryStatusConverter implements AttributeConverter<ImportHistoryStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ImportHistoryStatus status) {
        return status.getValue();
    }

    @Override
    public ImportHistoryStatus convertToEntityAttribute(Integer value) {
        return ImportHistoryStatus.of(value);
    }

}
