package ntukhpi.ddy.buzz.service.impl;

import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.repository.TrainRepository;
import ntukhpi.ddy.buzz.repository.WagonRepository;
import ntukhpi.ddy.buzz.service.WagonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WagonServiceImpl implements WagonService {
    private final WagonRepository wagonRepository;
    public WagonServiceImpl (WagonRepository wagonRepository){
        super();
        this.wagonRepository = wagonRepository;
    }

    @Override
    public List<Wagon> getAllWagons() {
        return wagonRepository.findAll();
    }

    @Override
    public Wagon saveWagon(Wagon wagon) {
        return wagonRepository.save(wagon);
    }

    @Override
    public Wagon getWagonById(Long id) {
        return wagonRepository.findById(id).orElse(null);
    }

    @Override
    public Wagon updateWagon(Long id, Wagon wagon) {
        wagon.setId(id);
        return wagonRepository.save(wagon);
    }

    @Override
    public void deleteWagonById(Long id) {
        wagonRepository.deleteById(id);
    }

    @Override
    public List<Wagon> getWagonsByType(wagonType wagonType) {
        return wagonRepository.findWagonsByWagonTypes(wagonType);
    }
}
