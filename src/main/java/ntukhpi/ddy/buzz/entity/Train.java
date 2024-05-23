package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.trainType.trainTypeConverter;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhuConverter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "wagon_id", nullable = false)
    private Wagon wagon;

    @Column(nullable = false, length = 50)
    private String pointVid;

    @Column(nullable = false, length = 50)
    private String pointDo;

    @Enumerated(EnumType.STRING)
    @Column(name = "variantRuhu", nullable = false, length = 12)
    @Convert(converter = variantRuhuConverter.class)
    private variantRuhu VariantRuhu;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeToGo;
    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeToArrive;

    @Column(nullable = false, length =  5)
    private double distance;

    public Train(String number,String trainTypes, Wagon[] wagon, String pointVid, String pointDo, String VariantRuhu, String timeToGo, String duration, double distance){
        this.id = 0L;
        this.TrainType = trainType.getByType(trainTypes);
        System.arraycopy(wagon, 0, this.wagon, 0, 10);
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
        this.distance = distance;
    }
    public Train(String number){
        this.id = 0L;
        this.number = number;
        this.TrainType = trainType.getTypesById(1);
        wagon = new Wagon("Кировоградський");
        this.pointVid = "Київський Вокзал";
        this.pointDo = "Харьківський Вокзал";
        this.VariantRuhu = variantRuhu.getVariantById(2);
        this.timeToGo = LocalTime.of(10, 55);
        this.duration = LocalTime.of(05, 10);
        this.timeToArrive = LocalTime.of(16, 05);
        this.distance = 500;
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
        sb.append("Тип вагона: ").append(wagon.getWagonTypes().getDisplayName()).append(": \n");
        sb.append("Місце відправлення: ").append(pointVid).append(", \n");
        sb.append("Місце призначення: ").append(pointDo).append(", \n");
        sb.append("Відстань: ").append(distance).append(", \n");
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
