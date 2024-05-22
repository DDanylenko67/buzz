package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;

import java.time.LocalDate;
import java.util.List;

public interface WagonService {
    List<Wagon> getAllWagons();
    Wagon saveWagon(Wagon wagon);
    Wagon getWagonById(Long id);
    Wagon updateWagon(Long id, Wagon wagon);
    void deleteWagonById(Long id);
    Wagon getWagonByType(wagonType wagonType);
}
