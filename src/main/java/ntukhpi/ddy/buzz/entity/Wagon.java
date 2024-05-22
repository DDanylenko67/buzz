package ntukhpi.ddy.buzz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonTypeConverter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wagon")
public class Wagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30,unique = true)
    private String factory;

    @Enumerated(EnumType.STRING)
    @Column(name = "wagonType", nullable = false, length = 20)
    @Convert(converter = wagonTypeConverter.class)
    private wagonType WagonType;

    @Column(nullable = false, length = 3)
    private int numberOfSeats;

    public Wagon(Long id, String factory, String WagonType, int number){
        this.id = id;
        this.factory = factory;
        this.WagonType = wagonType.getByType(WagonType);
        this.numberOfSeats = number;
    }
    public Wagon(String factory){
        this.id = 0L;
        this.factory = factory;
        this.WagonType = wagonType.coupe;
        this.numberOfSeats = 60;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("" + id + ": ");
        sb.append(factory).append(" - ");
        sb.append(WagonType.getDisplayName()).append(" - ");
        sb.append(numberOfSeats).append(" - ");
        return sb.toString();
    }
}