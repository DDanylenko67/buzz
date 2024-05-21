package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.trainType.trainTypeConverter;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhuConverter;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonTypeConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30,unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "trainType", nullable = false, length = 20)
    @Convert(converter = trainTypeConverter.class)
    private trainType TrainType;

    @Enumerated(EnumType.STRING)
    @Column(name = "wagonType", nullable = false, length = 20)
    @Convert(converter = wagonTypeConverter.class)
    private wagonType WagonType;


    @Column(nullable = false, length = 50)
    private String pointVid;

    @Column(nullable = false, length = 50)
    private String pointDo;

    @Enumerated(EnumType.STRING)
    @Column(name = "variantRuhu", nullable = false, length = 12)
    @Convert(converter = variantRuhuConverter.class)
    private variantRuhu VariantRuhu;

    @Column(nullable = false)
    private LocalTime timeToGo;

    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    private LocalTime timeToArrive;

    public Train(String number,String trainTypes, String wagonTypes, String pointVid, String pointDo, String VariantRuhu, String timeToGo, String duration){
        this.id = 0L;
        this.TrainType = trainType.getByType(trainTypes);
        this.WagonType = wagonType.getByType(wagonTypes);
        this.number = number;
        this.pointVid = pointVid;
        this.pointDo = pointDo;
        this.VariantRuhu = variantRuhu.getByVariant(VariantRuhu);
        LocalTime temp = LocalTime.parse(timeToGo, DateTimeFormatter.ofPattern("HH:mm"));
        this.timeToGo = temp;
        LocalTime tempDur = LocalTime.parse(duration, DateTimeFormatter.ofPattern("HH:mm"));
        this.duration = tempDur;
        temp = temp.plusHours(tempDur.getHour());
        temp = temp.plusMinutes(tempDur.getMinute());
        this.timeToArrive = temp;
    }
    public Train(String number){
        this.id = 0L;
        this.number = number;
        this.TrainType = trainType.getTypesById(1);
        this.WagonType = wagonType.getTypeById(4);
        this.pointVid = "Київський Вокзал";
        this.pointDo = "Харьківський Вокзал";
        this.VariantRuhu = variantRuhu.getVariantById(2);
        this.timeToGo = LocalTime.of(10, 55);
        this.duration = LocalTime.of(05, 10);
        this.timeToArrive = LocalTime.of(16, 05);
    }
    public String TimeToGoToSting() {
        long hours = timeToGo.getHour();
        long minutes = timeToGo.getMinute();
        return String.format("%02d:%02d", hours, minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        return number.equals(train.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
    public String getVRName() {
        return VariantRuhu.getDisplayName();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("" + id + ": ");
        sb.append("Номер потяга - ").append(number).append(", \n");
        sb.append("Тип потяга: ").append(TrainType.getDisplayName()).append(": \n");
        sb.append("Тип вагона: ").append(WagonType.getDisplayName()).append(": \n");
        sb.append("Місце відправлення: ").append(pointVid).append(", \n");
        sb.append("Місце призначення: ").append(pointDo).append(", \n");
        sb.append("Час відправлення: ").append(getTimeToGo()).append(", \n");
        sb.append("Вірант руху: ").append(VariantRuhu.getDisplayName()).append(": \n");
        sb.append("Час у дорозі: ").append(getDuration()).append(", \n");
        sb.append("Час прибуття: ").append(getTimeToArrive()).append(". \n").append("-------------\n");
        return sb.toString();
    }
    public LocalTime getDurationTime() {
        return duration;
    }
}
