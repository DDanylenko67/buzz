package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Train findByNumber(String number);
}
