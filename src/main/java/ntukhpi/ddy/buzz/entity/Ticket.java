package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.ddy.buzz.enums.documentType.documentType;
import ntukhpi.ddy.buzz.enums.documentType.documentTypeConverter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 80)
    private String owner;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false, length = 20)
    @Convert(converter = documentTypeConverter.class)
    private documentType documentType;


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

    public Ticket(Train train, String Owner, String document, int wagon, int seat, LocalDate localDate, double price, String email, String phone, documentType documentType) {
        if (wagon == 0 || seat == 0 || seat < 0 || wagon < 0) {
            System.out.println("Не може бути нульового вагону або місця");
        } else {
            this.id = 0L;
            this.train = train;
            this.owner = Owner;
            this.documentID = document;
            this.wagon = wagon;
            this.seat = seat;
            this.dateToGo = localDate;
            this.price = price;
            this.email = email;
            this.phone = phone;
            this.documentType = documentType;
        }
    }

    public Ticket(Train train, String Owner, String document, int wagon, int seat, LocalDate localDate, double price) {
        if (wagon == 0 || seat == 0 || seat < 0 || wagon < 0) {
            System.out.println("Не може бути нульового вагону або місця");
        } else {
            this.user = new User(0L, "", "", "", documentType.full, "", new ArrayList<>());
            this.id = 0L;
            this.train = train;
            this.owner = Owner;
            this.documentID = document;
            this.wagon = wagon;
            this.seat = seat;
            this.dateToGo = localDate;
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
        sb.append(documentType.toString()).append(", ");
        sb.append(email).append(", ");
        sb.append(phone).append(", ");
        if(user != null ){
            sb.append(user.toString()).append(", ");
        }
        sb.append(wagon).append(", ");
        sb.append(seat).append(", ");
        sb.append(TimeToGoTo()).append(", ");
        sb.append(price);
        return sb.toString();
    }
}
