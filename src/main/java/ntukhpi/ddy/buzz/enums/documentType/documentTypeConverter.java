package ntukhpi.ddy.buzz.enums.documentType;

import jakarta.persistence.AttributeConverter;
import ntukhpi.ddy.buzz.enums.trainType.trainType;

public class documentTypeConverter implements AttributeConverter<documentType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(documentType documentType) {
        return documentType.ordinal();
    }

    @Override
    public documentType convertToEntityAttribute(Integer codeTT) {
        return documentType.values()[codeTT];
    }
}
