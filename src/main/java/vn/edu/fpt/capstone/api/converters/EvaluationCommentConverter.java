package vn.edu.fpt.capstone.api.converters;

import vn.edu.fpt.capstone.api.constants.EvaluationComment;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EvaluationCommentConverter implements AttributeConverter<EvaluationComment, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EvaluationComment type) {
        return type.getValue();
    }

    @Override
    public EvaluationComment convertToEntityAttribute(Integer value) {
        return EvaluationComment.of(value);
    }
}
