package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.service.TariffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class TestCRUDTariff{
    @Autowired
    private TariffService tariffService;

    @Test
    void addWagons(){
        Tariff tariff = new Tariff(trainType.interCityPlus.getDisplayName());
        tariffService.saveTariff(tariff);
        Tariff tariff1 = new Tariff("2024-05-23", trainType.interCityPlus.getDisplayName(), wagonType.plackart.getDisplayName(), 10.48, 0.59, 5);
        tariffService.saveTariff(tariff1);
        Tariff tariff2 = new Tariff("2024-04-23", trainType.passenger.getDisplayName(), wagonType.sleep.getDisplayName(), 95.01, 4, 5);
        tariffService.saveTariff(tariff2);
    }
    @Test
    void addTariff(){
        Tariff tariff = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.coupe.getDisplayName(), 40.63, 3, 5);
        tariffService.saveTariff(tariff);
    }
    @Test
    void addTariffs(){
        Tariff tariff = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.coupe.getDisplayName(), 40.63, 3, 5);
        tariffService.saveTariff(tariff);
        Tariff tariff1 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.lux.getDisplayName(), 100.24, 4.38, 10);
        tariffService.saveTariff(tariff1);
        Tariff tariff2 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.sleep.getDisplayName(), 100.24, 4.38, 5);
        tariffService.saveTariff(tariff2);
        Tariff tariff3 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.plackart.getDisplayName(), 19.87, 1.5, 3);
        tariffService.saveTariff(tariff3);
        Tariff tariff4 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.second.getDisplayName(), 11.42, 1, 5);
        tariffService.saveTariff(tariff4);
        Tariff tariff5 = new Tariff("2024-05-23", trainType.fastfull.getDisplayName(), wagonType.firstSeated.getDisplayName(), 11.42, 1, 8);
        tariffService.saveTariff(tariff5);
        Tariff tariff6 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonType.coupe.getDisplayName(), 38.45, 2.5, 5);
        tariffService.saveTariff(tariff6);
        Tariff tariff7 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonType.lux.getDisplayName(), 95.01, 4, 10);
        tariffService.saveTariff(tariff7);
        Tariff tariff8 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonType.sleep.getDisplayName(), 95.01, 4, 5);
        tariffService.saveTariff(tariff8);
        Tariff tariff9 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonType.plackart.getDisplayName(), 18.59, 2.05, 3);
        tariffService.saveTariff(tariff9);
        Tariff tariff10 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonType.second.getDisplayName(), 10.48, 2.05, 3.5);
        tariffService.saveTariff(tariff10);
        Tariff tariff11 = new Tariff("2024-05-23", trainType.passenger.getDisplayName(), wagonType.firstSeated.getDisplayName(), 10.48, 1, 5);
        tariffService.saveTariff(tariff11);
        Tariff tariff12 = new Tariff("2024-05-23", trainType.interCityPlus.getDisplayName(), wagonType.interCitySecond.getDisplayName(), 60.63, 3, 6);
        tariffService.saveTariff(tariff12);
        Tariff tariff13 = new Tariff("2024-05-23", trainType.interCityPlus.getDisplayName(), wagonType.interCityFirst.getDisplayName(), 60.63, 3, 10);
        tariffService.saveTariff(tariff13);
        Tariff tariff14 = new Tariff("2024-05-23", trainType.interCity.getDisplayName(), wagonType.interCitySecond.getDisplayName(), 40.63, 3, 6);
        tariffService.saveTariff(tariff12);
        Tariff tariff15 = new Tariff("2024-05-23", trainType.interCity.getDisplayName(), wagonType.interCityFirst.getDisplayName(), 40.63, 3, 10);
        tariffService.saveTariff(tariff13);
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
        Tariff tariff = new Tariff("2024-05-24", trainType.interCity.getDisplayName(), wagonType.plackart.getDisplayName(), 10.48, 0.59, 3);
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
