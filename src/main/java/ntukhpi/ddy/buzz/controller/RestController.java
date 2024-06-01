package ntukhpi.ddy.buzz.controller;

import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final TrainService trainService;
    private final TicketService ticketService;
    @Autowired
    public RestController(TrainService trainService, TicketService ticketService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
    }
    @GetMapping("/api/citiesVid")
    public ResponseEntity<?> getBuzzData(){
        List<Train> trains = trainService.getAllTrains();
        List<String> citiesVid = new ArrayList<>();;
        for (Train train : trains){
            citiesVid.add(train.getPointVid());
        }
        return ResponseEntity.ok(citiesVid);
    }
    @GetMapping("/api/citiesDo")
    public ResponseEntity<?> getBuzzDataDO(){
        List<Train> trains = trainService.getAllTrains();
        List<String> citiesDo = new ArrayList<>();;
        for (Train train : trains){
            citiesDo.add(train.getPointDo());
        }
        return ResponseEntity.ok(citiesDo);
    }

    @PostMapping("/selectSeat")
    public String selectSeat(@RequestBody String seatNumber) {
        return "Вибрано місце №" + seatNumber;
    }
}
