package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Train findByNumber(String number);
    List<Train> findByVariantRuhuAndPointVidAndPointDo(variantRuhu variantRuhu,String pointVid, String pointDo);
}
