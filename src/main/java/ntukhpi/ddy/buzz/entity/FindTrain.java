package ntukhpi.ddy.buzz.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class FindTrain {
    private String pointVid;
    private String pointDo;
    private LocalDate date;

    public FindTrain(String pointVid, String pointDo, LocalDate date) {
        this.pointVid = pointVid;
        this.pointDo = pointDo;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Потяг[" +
                "Місто відправлення: '" + pointVid + '\'' +
                ", Місто прибуття: '" + pointDo + '\'' +
                ", дата: " + date +
                ']';
    }
}
