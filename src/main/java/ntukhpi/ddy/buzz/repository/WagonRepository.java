package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WagonRepository extends JpaRepository<Wagon, Long> {
    Wagon findByWagonType(wagonType wagonType);
}
