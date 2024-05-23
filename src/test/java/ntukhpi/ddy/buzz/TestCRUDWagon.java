package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.service.TrainService;
import ntukhpi.ddy.buzz.service.WagonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCRUDWagon {
    @Autowired
    private TrainService trainService;
    @Autowired
    private WagonService wagonService;

    @Test
    void addWagon(){
        Wagon wagon = new Wagon("Київський завод");
        wagonService.saveWagon(wagon);
        Wagon wagon1 = new Wagon(0L, "Харьківський завод", wagonType.second.getDisplayName(), 70);
        wagonService.saveWagon(wagon1);
        Wagon wagon2 = new Wagon(0L, "Дніпровський завод", wagonType.coupeFirst.getDisplayName(), 20);
        wagonService.saveWagon(wagon2);
    }
    @Test
    void getAllWagons(){
        System.out.println(wagonService.getAllWagons());
    }
    @Test
    void getAllWagonsByType(){
        System.out.println(wagonService.getWagonsByType(wagonType.second));
    }
    @Test
    void TestCRUDWagons(){
        Wagon wagon = new Wagon(0L, "Вінницький завод", wagonType.second.getDisplayName(), 65);
        System.out.println(wagon);
        wagonService.saveWagon(wagon);
        wagon.setFactory("Криворіжський завод");
        wagon.setNumberOfSeats(68);
        wagonService.updateWagon(5L, wagon);
        System.out.println(wagonService.getWagonsByType(wagonType.second));
        System.out.println(wagonService.getWagonById(5L));
        wagonService.deleteWagonById(5L);
    }
}
