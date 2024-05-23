package ntukhpi.ddy.buzz.service.impl;

import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.repository.TariffRepository;
import ntukhpi.ddy.buzz.repository.TicketRepository;
import ntukhpi.ddy.buzz.repository.TrainRepository;
import ntukhpi.ddy.buzz.service.TariffService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;

    public TariffServiceImpl (TariffRepository tariffRepository){
        super();
        this.tariffRepository = tariffRepository;
    }

    @Override
    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @Override
    public Tariff saveTariff(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff getTariffById(Long id) {
        return tariffRepository.findById(id).orElse(null);
    }

    @Override
    public Tariff updateTariff(Long id, Tariff tariff) {
        tariff.setId(id);
        return tariffRepository.save(tariff);
    }

    @Override
    public void deleteTariffById(Long id) {
        tariffRepository.deleteById(id);
    }

    @Override
    public Tariff getTariffByDate(LocalDate localDate) {
        return tariffRepository.findByDate(localDate);
    }
    @Override
    public Tariff getTariffByTypes(wagonType wagonType, trainType trainType) {
        return tariffRepository.findTariffByWagonTypesAndTrainTypes(wagonType, trainType);
    }
}
