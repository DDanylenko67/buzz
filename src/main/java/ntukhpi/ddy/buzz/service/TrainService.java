package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;

import java.time.LocalDate;
import java.util.List;

public interface TrainService {
    List<Train> getAllTrains();
    Train saveTrain(Train train);
    Train getTrainById(Long id);
    Train updateTrain(Long id, Train train);
    void deleteTrainById(Long id);
    Train getTrainByNumber(String number);
    List<Train> findTrainsByTypeAndPoins(variantRuhu variantRuhu,String pointVid, String pointDo);
}
