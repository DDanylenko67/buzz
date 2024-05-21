package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;
    @Column(nullable = false, length = 30)
    private String fistName;
    @Column(nullable = false, length = 30)
    private String lastName;
    @Column(nullable = false, length = 30)
    private String patronymic;
    @Column(nullable = false, length = 10)
    private String documentID;
    @Column(nullable = false)
    private int wagon;
    @Column(nullable = false)
    private int seat;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timeToGo;
    public Ticket(Train train, String fistName, String lastName, String patronymic, String document, int wagon, int seat, String timeToGo){
            if(wagon == 0 || seat == 0 || seat < 0 || wagon < 0){
                System.out.println("Не може бути нульового вагону або місця");
            }else{
                this.id = 0L;
                this.train = train;
                this.fistName = fistName;
                this.lastName = lastName;
                this.patronymic = patronymic;
                this.documentID = document;
                this.wagon = wagon;
                this.seat = seat;
                this.timeToGo = LocalDate.parse(timeToGo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
    }
    public String TimeToGoTo() {
        long day = timeToGo.getDayOfMonth();
        long month = timeToGo.getMonthValue();
        long year = timeToGo.getYear();
        return String.format("%02d.%02d.%04d", day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("" + id + ": ");
        sb.append(fistName).append(" - ");
        sb.append(lastName).append(" - ");
        sb.append(patronymic).append(" - ");
        sb.append(train.getNumber()).append(", ");
        sb.append(documentID).append(", ");
        sb.append(wagon).append(", ");
        sb.append(seat).append(", ");
        sb.append(TimeToGoTo());
        return sb.toString();
    }

}
