package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.Tariff;

import java.time.LocalDate;
import java.util.List;

public interface TariffService {
    List<Tariff> getAllTariffs();
    Tariff saveTariff(Tariff tariff);
    Tariff getTariffById(Long id);
    Tariff updateTariff(Long id, Tariff tariff);
    void deleteTariffById(Long id);
    Tariff getTariffByDate(LocalDate localDate);
}
