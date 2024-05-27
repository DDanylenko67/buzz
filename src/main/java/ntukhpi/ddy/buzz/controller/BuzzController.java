package ntukhpi.ddy.buzz.controller;



import ntukhpi.ddy.buzz.entity.FindTrain;
import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.service.TariffService;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BuzzController {
    private final TrainService trainService;
    private final TicketService ticketService;
    private final TariffService tariffService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String minDate = LocalDate.now().plusDays(1).format(formatter);
    String maxDate = LocalDate.now().plusMonths(2).format(formatter);
    @Autowired
    public BuzzController(TrainService trainService, TicketService ticketService, TariffService tariffService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
        this.tariffService = tariffService;
    }
    @GetMapping("/buzz")
    public String showPage1(Model model){
        model = authentication(model);
        List<Ticket> last4Tickets = getLast4Tickets(ticketService.getAllTickets());
        List<Train> trains = trainService.getAllTrains();
        List<String> citiesDo = new ArrayList<>();;
        List<String> citiesVid = new ArrayList<>();;
        for (Train train : trains){
            citiesDo.add(train.getPointDo());
            citiesVid.add(train.getPointVid());
        }

        int count = ticketService.getAllTickets().size();
        model.addAttribute("findTrain", new FindTrain("", "", LocalDate.now()));
        model.addAttribute("last4Tickets", last4Tickets);
        model.addAttribute("citiesDo", citiesDo);
        model.addAttribute("citiesVid", citiesVid);
        model.addAttribute("count", count);
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);
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

    @GetMapping("/buzz/trains")
    String findTrain(Model model, FindTrain train){
        model = authentication(model);
        List<variantRuhu> variantRuhus = findVariant(train);
        List<Train> trains = new ArrayList<>();
        for(variantRuhu VariantRuhu : variantRuhus){
            trains.addAll(trainService.findTrainsByTypeAndPoins(VariantRuhu,train.getPointVid(), train.getPointDo()));
        }
        model.addAttribute("count", trains.size());
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);
        model.addAttribute("find", train);
        model.addAttribute("trains", trains);
        model.addAttribute("service", tariffService);
        model.addAttribute("dateOfGo", train.getDate());
        for(Train train1 : trains){
            System.out.println(train1.getTrainType() + " " + train1.getWagon().getWagonTypes());
            Tariff tariff = tariffService.getTariffByTypes(train1.getWagon().getWagonTypes(), train1.getTrainType());
            if (tariff != null) {
                System.out.println(tariff.compPrice(train.getDate(), train1.getDistance(), train1.getTrainType()));
            } else {
                System.out.println("Tariff not found for train: " + train1);
            }

        }

        return "/buzz/trains";
    }

    private Model authentication(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }

        return model;
    }

    private List<variantRuhu> findVariant(FindTrain train){
        List<variantRuhu> variantRuhus = new ArrayList<>();
        variantRuhus.add(variantRuhu.daily);
        if (train.getDate().getDayOfMonth() % 2 == 0){
            variantRuhus.add(variantRuhu.paired);
        }
        else {
            variantRuhus.add(variantRuhu.unpaired);
        }
        if(train.getDate().isAfter(LocalDate.of(train.getDate().getYear(), 5, 31)) &&
                train.getDate().isBefore(LocalDate.of(train.getDate().getYear(), 9, 1))){
            variantRuhus.add(variantRuhu.seasonSummer);
        }
        if(train.getDate().isAfter(LocalDate.of(train.getDate().getYear(), 11, 30)) &&
                train.getDate().isBefore(LocalDate.of(train.getDate().plusYears(1).getYear(), 1, 1))){
            variantRuhus.add(variantRuhu.seasonDecember);
        }
        return variantRuhus;
    }
}
