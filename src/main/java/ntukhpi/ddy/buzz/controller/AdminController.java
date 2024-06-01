package ntukhpi.ddy.buzz.controller;

import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminController {
    private final TrainService trainService;
    private final TicketService ticketService;
    @Autowired
    public AdminController(TrainService trainService, TicketService ticketService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
    }
    @GetMapping("/admin")
    public String showPage(Model model){
        List<Train> trains = trainService.getAllTrains();
        model.addAttribute("trains", trains);
        return "admin/admin";
    }
    @GetMapping("/admin/trains/report/{idTrain}")
    public String report(Model model,  @PathVariable Long idTrain){
        model.addAttribute("train", trainService.getTrainById(idTrain));
        model.addAttribute("max", LocalDate.now().minusDays(1));
        model.addAttribute("min", LocalDate.now().minusYears(1));
        return "/admin/report";
    }
}
