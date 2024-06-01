package ntukhpi.ddy.buzz.controller;



import ntukhpi.ddy.buzz.entity.*;
import ntukhpi.ddy.buzz.enums.documentType.documentType;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.service.TariffService;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import ntukhpi.ddy.buzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class BuzzController {
    private final TrainService trainService;
    private final TicketService ticketService;
    private final TariffService tariffService;
    private final UserService userService;
    private static LocalDate data;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String minDate = LocalDate.now().plusDays(1).format(formatter);
    String maxDate = LocalDate.now().plusMonths(2).format(formatter);
    @Autowired
    public BuzzController(TrainService trainService, TicketService ticketService, TariffService tariffService, UserService userService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
        this.tariffService = tariffService;
        this.userService = userService;
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
            Collections.reverse(tickets);
            return tickets;
        } else {
            Collections.reverse(tickets);
            return tickets.subList(0, 4);
        }
    }
    @GetMapping("/buzz/trains")
    String findTrain(Model model, FindTrain train){
        model = authentication(model);
        List<variantRuhu> variantRuhus = findVariant(train);
        List<Train> trains = new ArrayList<>();
        data = train.getDate();
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
        model.addAttribute("ticketService", ticketService);

        return "/buzz/trains";
    }



    @GetMapping("/buzz/trains/sort")
    public String sortTrains(@RequestParam("sortField") String sortField, @RequestParam("pointVid") String pointVid,
                             @RequestParam("pointDo") String pointDo, @RequestParam("date") LocalDate date,
                             Model model) {
        model = authentication(model);
        FindTrain findTrain = new FindTrain(pointVid, pointDo, date);
        List<variantRuhu> variantRuhus = findVariant(findTrain);
        List<Train> trains = new ArrayList<>();
        for(variantRuhu VariantRuhu : variantRuhus){
            trains.addAll(trainService.findTrainsByTypeAndPoins(VariantRuhu,findTrain.getPointVid(), findTrain.getPointDo()));
        }
        switch (sortField) {
            case "price":
                trains.sort((t1, t2) -> tariffService.getTariffByTypes(t1.getWagon(), t1.getTrainType()).compPrice(date, t1.getDistance(), t1.getTrainType()).
                        compareTo(tariffService.getTariffByTypes(t2.getWagon(), t2.getTrainType()).compPrice(date, t2.getDistance(), t2.getTrainType())));
                break;
            case "duration":
                trains.sort(Comparator.comparing(Train::getDuration));
                break;
            case "departureTime":
                trains.sort(Comparator.comparing(Train::getTimeToGo));
                break;
        }
        for(Train train : trains){
            System.out.println("--------------------- " + train.getNumber() + " ---------------------------------------------");
            System.out.println(tariffService.getTariffByTypes(train.getWagon(), train.getTrainType()).compPrice(date, train.getDistance(), train.getTrainType()));
            System.out.println("--------------------- ---------------------------------------------");
        }
        model.addAttribute("count", trains.size());
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);
        model.addAttribute("find", findTrain);
        model.addAttribute("trains", trains);
        model.addAttribute("service", tariffService);
        model.addAttribute("dateOfGo", findTrain.getDate());
        model.addAttribute("ticketService", ticketService);
        return "/buzz/trains";
    }
    @GetMapping("/buzz/cabinet")
    public String cab(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        List<Ticket> tickets =  ticketService.findTickets(user);
        tickets.sort(Comparator.comparing(Ticket::getDateToGo));
        model.addAttribute("tickets", tickets);
        return "buzz/cabinet";
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
    private List<variantRuhu> findVariant(FindTrain train) {
        List<variantRuhu> variantRuhus = new ArrayList<>();
        variantRuhus.add(variantRuhu.daily);
        if (train.getDate().getDayOfMonth() % 2 == 0) {
            variantRuhus.add(variantRuhu.paired);
        }
        if (train.getDate().getDayOfMonth() % 2 != 0) {
                variantRuhus.add(variantRuhu.unpaired);
            }
            if (train.getDate().isAfter(LocalDate.of(train.getDate().getYear(), 5, 31)) &&
                    train.getDate().isBefore(LocalDate.of(train.getDate().getYear(), 9, 1))) {
                variantRuhus.add(variantRuhu.seasonSummer);
            }
            if (train.getDate().isAfter(LocalDate.of(train.getDate().getYear(), 11, 30)) &&
                    train.getDate().isBefore(LocalDate.of(train.getDate().plusYears(1).getYear(), 1, 1))) {
                variantRuhus.add(variantRuhu.seasonDecember);
            }
        return variantRuhus;
    }
}
