package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Tariff findByDate(LocalDate localDate);
    Tariff findByWagonAndTrainTypes(Wagon wagon, trainType trainType);
}
