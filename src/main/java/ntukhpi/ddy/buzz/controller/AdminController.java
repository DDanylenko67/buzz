package ntukhpi.ddy.buzz.controller;

import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
