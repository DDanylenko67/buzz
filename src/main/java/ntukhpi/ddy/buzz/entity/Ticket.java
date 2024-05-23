package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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

    @Column(nullable = false, length = 60)
    private String owner;
    @Column(nullable = false, length = 10)
    private String documentID;

    @Column(nullable = false, length = 3)
    private int wagon;
    @Column(nullable = false, length = 3)
    private int seat;
    @Column(nullable = false, length = 10)
    private double price;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateToGo;
    public Ticket(Train train, String Owner, String document, int wagon, int seat, String dateToGo, double price){
            if(wagon == 0 || seat == 0 || seat < 0 || wagon < 0){
                System.out.println("Не може бути нульового вагону або місця");
            }else{
                this.id = 0L;
                this.train = train;
                this.owner = Owner;
                this.documentID = document;
                this.wagon = wagon;
                this.seat = seat;
                this.dateToGo = LocalDate.parse(dateToGo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                this.price = price;
            }
    }
    public String TimeToGoTo() {
        long day = dateToGo.getDayOfMonth();
        long month = dateToGo.getMonthValue();
        long year = dateToGo.getYear();
        return String.format("%02d.%02d.%04d", day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("" + id + ": ");
        sb.append(owner).append(" - ");
        sb.append(train.getNumber()).append(", ");
        sb.append(documentID).append(", ");
        sb.append(wagon).append(", ");
        sb.append(seat).append(", ");
        sb.append(TimeToGoTo()).append(", ");
        sb.append(price);
        return sb.toString();
    }
    public Long getId() {
        return id;
    }

    public Train getTrain() {
        return train;
    }


    public String getOwner() {
        return owner;
    }

    public String getDocumentID() {
        return documentID;
    }

    public int getWagon() {
        return wagon;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDateToGo() {
        return dateToGo;
    }

}
