package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Tariff;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Tariff findByDate(LocalDate localDate);
}
