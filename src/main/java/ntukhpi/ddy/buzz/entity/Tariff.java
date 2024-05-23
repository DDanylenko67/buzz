package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.trainType.trainTypeConverter;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonTypeConverter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tariff")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "trainType", nullable = false, length = 20)
    @Convert(converter = trainTypeConverter.class)
    private trainType trainTypes;

    @Enumerated(EnumType.STRING)
    @Column(name = "wagonType", nullable = false, length = 20)
    @Convert(converter = wagonTypeConverter.class)
    private wagonType wagonTypes;

    @Column(nullable = false, length = 10)
    private double basePrice;

    @Column(nullable = false, length = 4)
    private double decadeSum;

    @Column(nullable = false, length = 10)
    private double baggageBasePrice;

    @Column(nullable = false, length = 4)
    private double decadeSumBaggage;

    @Column(nullable = false, length = 4)
    private double indexComfort;

    public Tariff(String date, String TrainTypes, String WagonType, double basePrice, double decadeSum, double indexComfort) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.trainTypes = trainType.getByType(TrainTypes);
        this.wagonTypes = wagonType.getByType(WagonType);
        this.decadeSum = decadeSum;
        this.basePrice = basePrice;
        this.indexComfort = indexComfort;
    }

    public Tariff(String TrainTypes){
        this.id = 0L;
        this.date = LocalDate.now();
        this.trainTypes = trainType.getByType(TrainTypes);
        this.wagonTypes = wagonType.interCitySecond;
        this.decadeSum = 0.66;
        this.basePrice = 11.42;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("" + id + ": ");
        sb.append(date).append(" - ");
        sb.append(trainTypes.getDisplayName()).append(" - ");
        sb.append(wagonTypes.getDisplayName()).append(" - ");
        sb.append(decadeSum).append(" - ");
        sb.append(basePrice);
        return sb.toString();
    }

    //визначається шляхом множення тарифу на коефіцієнт індексації за календарними періодами  та на коефіцієнт зниження (підвищення).
    public double compPrice(Ticket ticket) {
        double koefIndex = 1;
        double indexInc = 1;
        double price = 0;

        LocalDate data = ticket.getDateToGo();
        if (data.getDayOfWeek().equals(DayOfWeek.FRIDAY) || data.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            indexInc = 1.1;
        } else if (data.getDayOfWeek().equals(DayOfWeek.TUESDAY) || data.getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
            indexInc = 0.9;
        }
        else if (ticket.getTrain().getWagon().getWagonTypes().getDisplayName().equals(wagonType.interCitySecond.getDisplayName())) {
            long day = ChronoUnit.DAYS.between(LocalDate.now(), data);
            if (day >= 30) {
                koefIndex = 0.85;
            } else if (day >= 25) {
                koefIndex = 0.9;
            } else if (day >= 15) {
                koefIndex = 0.95;
            } else if (day >= 5) {
                koefIndex = 1;
            } else if (day >= 2) {
                koefIndex = 1.1;
            } else {
                koefIndex = 1.15;
            }
        } else if (ticket.getTrain().getWagon().getWagonTypes().getDisplayName().equals(wagonType.sleep.getDisplayName())) {
                koefIndex = 1.02;
            } else if (ticket.getTrain().getWagon().getWagonTypes().getDisplayName().equals(wagonType.coupeFirst.getDisplayName())) {
                koefIndex = 1.5;
            } else if (ticket.getTrain().getWagon().getWagonTypes().getDisplayName().equals(wagonType.second.getDisplayName())){
                koefIndex = 1.15;

        } else {
            koefIndex = findCoef(data);
        }
        price = basePrice;
        for(int i = 0; i < ticket.getTrain().getDistance(); i+=10){
             price += decadeSum;
        }
        System.out.println("Price = " + price + " KoefIndex = " + koefIndex + " IndexInc = " + indexInc + " IndexComfort  =" + indexComfort);
        price = price * koefIndex * indexComfort * indexInc;
        return price;
    }


    private double findCoef(LocalDate date){
        if(date.isAfter(LocalDate.of(date.getYear(), 1, 1)) &&  date.isBefore(LocalDate.of(date.getYear(), 1, 21))){
            return 1.02;
        } if(date.isAfter(LocalDate.of(date.getYear(), 1, 20)) &&  date.isBefore(LocalDate.of(date.getYear(), 2, 1))){
            return 0.86;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 1, 31)) &&  date.isBefore(LocalDate.of(date.getYear(), 3, 1))){
            return 0.95;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 2, 29)) &&  date.isBefore(LocalDate.of(date.getYear(), 4, 1))){
            return 1.01;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 3, 31)) &&  date.isBefore(LocalDate.of(date.getYear(), 5, 1))){
            return 1.02;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 4, 27)) &&  date.isBefore(LocalDate.of(date.getYear(), 5, 9))){
            return 1.03;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 5, 9)) &&  date.isBefore(LocalDate.of(date.getYear(), 5, 10))){
            return 0.8;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 5, 9)) &&  date.isBefore(LocalDate.of(date.getYear(), 6, 1))){
            return 1.01;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 5, 31)) &&  date.isBefore(LocalDate.of(date.getYear(), 9, 1))){
            return 1.07;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 8, 31)) &&  date.isBefore(LocalDate.of(date.getYear(), 9, 29))){
            return 1.02;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 9, 30)) &&  date.isBefore(LocalDate.of(date.getYear(), 12, 23))){
            return 0.93;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 12, 24)) &&  date.isBefore(LocalDate.of(date.getYear(), 12, 29))){
            return 1.1;
        }
        else if(date.isAfter(LocalDate.of(date.getYear(), 12, 30))){
            return 0.7;
        }
        return 1;
    }
}
