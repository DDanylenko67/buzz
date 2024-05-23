package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WagonRepository extends JpaRepository<Wagon, Long> {
    List<Wagon> findWagonsByWagonTypes(wagonType wagonType);
}
