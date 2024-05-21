package ntukhpi.ddy.buzz.enums.wagonType;

import jakarta.persistence.AttributeConverter;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;

public class wagonTypeConverter implements AttributeConverter<wagonType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(wagonType wagonType) {
        return wagonType.ordinal();
    }

    @Override
    public wagonType convertToEntityAttribute(Integer codeWT) {
        return wagonType.values()[codeWT];
    }
}
