package ntukhpi.ddy.buzz.enums.trainType;

import jakarta.persistence.AttributeConverter;

public class trainTypeConverter implements AttributeConverter<trainType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(trainType trainType) {
        return trainType.ordinal();
    }

    @Override
    public trainType convertToEntityAttribute(Integer codeTT) {
        return trainType.values()[codeTT];
    }
}
