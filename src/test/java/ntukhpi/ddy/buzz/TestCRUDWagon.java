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
        Wagon wagon1 = new Wagon(0L, "Крюківський завод", wagonType.lux.getDisplayName(), 20);
        wagonService.saveWagon(wagon1);
        Wagon wagon5 = new Wagon(0L, "Тверський завод", wagonType.sleep.getDisplayName(), 18);
        wagonService.saveWagon(wagon5);
        Wagon wagon6 = new Wagon(0L, "Тверський завод", wagonType.coupe.getDisplayName(), 40);
        wagonService.saveWagon(wagon6);
        Wagon wagon7= new Wagon(0L, "Тверський завод", wagonType.plackart.getDisplayName(), 54);
        wagonService.saveWagon(wagon7);
        Wagon wagon8= new Wagon(0L, "Крюківський завод", wagonType.coupeFirst.getDisplayName(), 45);
        wagonService.saveWagon(wagon8);
        Wagon wagon9= new Wagon(0L, "Крюківський завод", wagonType.second.getDisplayName(), 68);
        wagonService.saveWagon(wagon9);
        Wagon wagon10= new Wagon(0L, "Крюківський завод", wagonType.firstSeated.getDisplayName(), 30);
        wagonService.saveWagon(wagon10);
        Wagon wagon2 = new Wagon(0L, "Крюківський завод", wagonType.coupe.getDisplayName(), 40);
        wagonService.saveWagon(wagon2);
        Wagon wagon3 = new Wagon(0L, "Hyundai", wagonType.interCityFirst.getDisplayName(), 60);
        wagonService.saveWagon(wagon3);
        Wagon wagon4 = new Wagon(0L, "Skoda", wagonType.interCitySecond.getDisplayName(), 80);
        wagonService.saveWagon(wagon4);
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
