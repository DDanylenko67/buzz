package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.repository.TicketRepository;
import ntukhpi.ddy.buzz.repository.TrainRepository;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import ntukhpi.ddy.buzz.service.WagonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCRUDTrain {
    @Autowired
    private TrainService trainService;
    @Autowired
    private WagonService wagonService;

    @Test
    void addTrains(){
        wagonService.saveWagon(new Wagon("Кировоградський завод"));
        Train train = new Train("710");
        train.setId(1L);
        trainService.saveTrain(train);
        Train train1 = new Train("711");
        train1.setId(2L);
        trainService.saveTrain(train1);
        Train train2 = new Train("712");
        train2.setId(3L);
        trainService.saveTrain(train2);
    }

    @Test
    void getALl(){
        System.out.println(trainService.getAllTrains());
    }
    @Test
    void getTrainByID(){
        System.out.println(trainService.getTrainById(1L));
    }
    @Test
    void updateTrainByID(){
        Train temp = trainService.getTrainById(1L);
        System.out.println(temp);
        temp.setDistance(600);
        temp.setVariantRuhu(variantRuhu.daily);
        trainService.updateTrain(1L, temp);
        System.out.println(trainService.getTrainById(1L));
    }
    @Test
    void getTrainByNumber(){
        System.out.println(trainService.getTrainByNumber("710"));
    }
    @Test
    void deleteTrainByID(){
        trainService.saveTrain(new Train("700"));
        trainService.deleteTrainById(4L);
    }

}
