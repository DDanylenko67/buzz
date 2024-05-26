package ntukhpi.ddy.buzz.controller;



import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BuzzController {
    private final TrainService trainService;
    private final TicketService ticketService;
    @Autowired
    public BuzzController(TrainService trainService, TicketService ticketService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
    }
    @GetMapping("/buzz")
    public String showPage1(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
        List<Ticket> last4Tickets = getLast4Tickets(ticketService.getAllTickets());
        List<Train> trains = trainService.getAllTrains();
        List<String> citiesDo = new ArrayList<>();;
        List<String> citiesVid = new ArrayList<>();;
        for (Train train : trains){
            citiesDo.add(train.getPointDo());
            citiesVid.add(train.getPointVid());
        }

        int count = ticketService.getAllTickets().size();
        model.addAttribute("last4Tickets", last4Tickets);
        model.addAttribute("citiesDo", citiesDo);
        model.addAttribute("countVid", citiesVid);
        model.addAttribute("count", count);
        return "/buzz/buzz";
    }
    private List<Ticket> getLast4Tickets(List<Ticket> tickets) {
        int size = tickets.size();
        if (size <= 4) {
            return tickets;
        } else {
            return tickets.subList(size - 4, size);
        }
    }
}
