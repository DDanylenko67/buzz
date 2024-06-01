package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.service.TariffService;
import ntukhpi.ddy.buzz.service.WagonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class TestCRUDTariff{
    @Autowired
    private TariffService tariffService;
    @Autowired
    private WagonService wagonService;

    @Test
    void addWagons(){
        Tariff tariff = new Tariff(trainType.interCityPlus.getDisplayName());
        tariffService.saveTariff(tariff);
        Tariff tariff1 = new Tariff("2024-05-23", trainType.interCityPlus.getDisplayName(), wagonService.getWagonById(9L), 10.48, 0.59, 5);
        tariffService.saveTariff(tariff1);
        Tariff tariff2 = new Tariff("2024-04-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(2L), 95.01, 4, 5);
        tariffService.saveTariff(tariff2);
    }
    @Test
    void addTariff(){
        Tariff tariff = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(3L), 40.63, 3, 5);
        tariffService.saveTariff(tariff);
    }
    @Test
    void addTariffs(){
        Tariff tariff = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(3L), 40.63, 3, 4);
        tariffService.saveTariff(tariff);
        Tariff tariff1 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(1L), 100.24, 4.38, 7);
        tariffService.saveTariff(tariff1);
        Tariff tariff2 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(2L), 100.24, 4.38, 4);
        tariffService.saveTariff(tariff2);
        Tariff tariff3 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(4L), 19.87, 1.5, 3);
        tariffService.saveTariff(tariff3);
        Tariff tariff4 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(7L), 11.42, 1, 4);
        tariffService.saveTariff(tariff4);
        Tariff tariff5 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(6L), 11.42, 1, 6);
        tariffService.saveTariff(tariff5);
        Tariff tariff6 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(3L), 38.45, 2.5, 4);
        tariffService.saveTariff(tariff6);
        Tariff tariff7 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(1L), 95.01, 4, 8);
        tariffService.saveTariff(tariff7);
        Tariff tariff8 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(2L), 95.01, 4, 4);
        tariffService.saveTariff(tariff8);
        Tariff tariff9 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(4L), 18.59, 2.05, 2);
        tariffService.saveTariff(tariff9);
        Tariff tariff10 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(),wagonService.getWagonById(7L), 10.48, 2.05, 3);
        tariffService.saveTariff(tariff10);
        Tariff tariff11 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(6L), 10.48, 1, 4);
        tariffService.saveTariff(tariff11);
        Tariff tariff12 = new Tariff("2024-05-23", trainType.interCityPlus.getDisplayName(), wagonService.getWagonById(10L), 60.63, 3, 5);
        tariffService.saveTariff(tariff12);
        Tariff tariff13 = new Tariff("2024-05-23", trainType.interCityPlus.getDisplayName(), wagonService.getWagonById(9L), 60.63, 3, 8);
        tariffService.saveTariff(tariff13);
        Tariff tariff14 = new Tariff("2024-05-23", trainType.interCity.getDisplayName(), wagonService.getWagonById(10L), 40.63, 3, 4);
        tariffService.saveTariff(tariff14);
        Tariff tariff15 = new Tariff("2024-05-23", trainType.interCity.getDisplayName(), wagonService.getWagonById(9L), 40.63, 3, 7);
        tariffService.saveTariff(tariff15);
        Tariff tariff16 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(5L), 38.45, 2.5, 6);
        tariffService.saveTariff(tariff16);
        Tariff tariff17 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonService.getWagonById(8L), 38.45, 2.5, 4);
        tariffService.saveTariff(tariff17);
        Tariff tariff18 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(5L), 40.63, 3, 6);
        tariffService.saveTariff(tariff18);
        Tariff tariff19 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonService.getWagonById(8L), 40.63, 3, 4);
        tariffService.saveTariff(tariff19);
    }
    @Test
    void getAllWagons(){
        System.out.println(tariffService.getAllTariffs());
    }
    @Test
    void getAllWagonsByDate(){
        String test = "2024-04-23";
        System.out.println(tariffService.getTariffByDate(LocalDate.of(2024, 4, 23)));
    }
    @Test
    void TestCRUDWagons(){
        Tariff tariff = new Tariff("2024-05-24", trainType.interCity.getDisplayName(), wagonService.getWagonById(4L), 10.48, 0.59, 3);
        System.out.println(tariff);
        tariffService.saveTariff(tariff);
        tariff.setDate(LocalDate.of(2024, 5, 25));
        tariff.setBasePrice(10.6);
        tariffService.updateTariff(4L, tariff);
        System.out.println(tariffService.getTariffById(4L));
        tariffService.deleteTariffById(4L);
        System.out.println(tariff.compPrice(LocalDate.of(2024, 6, 1), 500, trainType.interCityPlus));
        System.out.println(tariff.compPrice(LocalDate.of(2024, 7, 1), 500,trainType.interCityPlus));
    }

}
