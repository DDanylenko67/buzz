package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface TariffService {
    List<Tariff> getAllTariffs();
    Tariff saveTariff(Tariff tariff);
    Tariff getTariffById(Long id);
    Tariff updateTariff(Long id, Tariff tariff);
    void deleteTariffById(Long id);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Tariff getTariffByDate(LocalDate localDate);
    Tariff getTariffByTypes(Wagon wagon, trainType trainType);
}
