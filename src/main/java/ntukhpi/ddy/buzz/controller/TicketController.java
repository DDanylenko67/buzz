package ntukhpi.ddy.buzz.controller;

import jakarta.servlet.http.HttpSession;
import ntukhpi.ddy.buzz.entity.*;
import ntukhpi.ddy.buzz.enums.documentType.documentType;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Controller
public class TicketController {
    private final WagonService wagonService;
    private final TicketService ticketService;
    private final TrainService trainService;
    private final UserService userService;
    private final TariffService tariffService;
    private final String TICKET_TEXT_INS = "ЗАМОВЛЕННЯ КВИТКА";
    @Autowired
    public TicketController(WagonService wagonService, TrainService trainService, TicketService ticketService, UserService userService, TariffService tariffService) {
        this.wagonService = wagonService;
        this.trainService = trainService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.tariffService = tariffService;
    }
    @GetMapping("/buzz/trains/seat/{trainId}")
    public String seat(Model model, @PathVariable Long trainId, @RequestParam LocalDate date, @RequestParam("wagon") int wagonNumber){
        authentication(model);
        Counter wagon = new Counter(wagonNumber);
        List<Ticket> tickets = ticketService.findTickets(trainId, date);
        Train train = trainService.getTrainById(trainId);
        System.out.println("-==================================");
        System.out.println(tickets.size());
        System.out.println("-==================================");
        FindTrain findTrain = new FindTrain(train.getPointVid(), train.getPointDo(), date);
        int[] i = new int[train.getWagon().getNumberOfSeats()+1];
        for(Ticket ticket:tickets){
            if(wagon.getWagon() == ticket.getWagon()){
                i[ticket.getSeat()] = 1;
            }
        }
        model.addAttribute("array", i);
        model.addAttribute("train", trainService.getTrainById(trainId));
        model.addAttribute("date", date);
        model.addAttribute("findTrain", findTrain);
        model.addAttribute("wagon", wagon);
        model.addAttribute("number", trainService.getTrainById(trainId).getWagon().getNumberOfSeats());
        model.addAttribute("tickets", tickets);
        return "/buzz/seat";
    }
    @GetMapping("/buzz/trains/ticket")
    public String buyTicket(Model model, @RequestParam Long trainId, @RequestParam LocalDate date,
                            @RequestParam int wagon, @RequestParam int seat){
        authentication(model);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Train train = trainService.getTrainById(trainId);
        Ticket newTicket = new Ticket(train, "", "", wagon, seat, date,
                tariffService.getTariffByTypes(train.getWagon(), train.getTrainType()).compPrice(date, train.getDistance(), train.getTrainType()),
                "", "", documentType.full);
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println(username);
            User User1 = userService.findUserByEmail(username);
            if(User1 != null){
                newTicket.setUser(User1);
                newTicket.setOwner(User1.getName());
                newTicket.setEmail(User1.getEmail());
                newTicket.setPhone(User1.getPhone());
                newTicket.setDocumentType(User1.getDocumentType());
            }
        }
        model.addAttribute("ticket", newTicket);
        model.addAttribute("titleTicket", TICKET_TEXT_INS);
        model.addAttribute("errorString", null);
        return "buzz/ticket";
    }
    @GetMapping("/paypal/{trainID}")
    public String pay(Model model, @PathVariable Long trainID,  @ModelAttribute("ticket") Ticket ticketToSave,
                      @RequestParam LocalDate date,
                      @RequestParam int wagon, @RequestParam int seat, @RequestParam Long id, @RequestParam double price, @RequestParam String documentID){
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(ticketToSave.getDocumentType().getDisplayName().equals(documentType.discount.getDisplayName()) ||
                ticketToSave.getDocumentType().getDisplayName().equals(documentType.children.getDisplayName())){
            price /= 2;
        }
        ticketToSave.setDateToGo(date);
        ticketToSave.setWagon(wagon);
        ticketToSave.setDocumentID(documentID);
        ticketToSave.setSeat(seat);
        ticketToSave.setPrice(price);
        ticketToSave.setTrain(trainService.getTrainById(trainID));
        model.addAttribute("ticket", ticketToSave);
        System.out.println(ticketToSave);
        return "buzz/pay";
    }
    @PostMapping("/buzz/trains/save/{trainId}")
    public String SaveTrain(Model model, @PathVariable Long trainId,
                            @ModelAttribute("ticket") Ticket ticketToSave,  @RequestParam LocalDate date,
                            @RequestParam int wagon, @RequestParam int seat, @RequestParam Long id, @RequestParam double price,
                            @RequestParam String owner, @RequestParam documentType documentType, @RequestParam String documentID,
                            @RequestParam String email, @RequestParam String phone){
        ticketToSave.setTrain(trainService.getTrainById(trainId));
        date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ticketToSave.setDateToGo(date);
        ticketToSave.setWagon(wagon);
        ticketToSave.setSeat(seat);
        ticketToSave.setPrice(price);
        ticketToSave.setOwner(owner);
        ticketToSave.setDocumentType(documentType);
        ticketToSave.setDocumentID(documentID);
        ticketToSave.setEmail(email);
        ticketToSave.setPhone(phone);
        LocalDate temp = ticketToSave.getDateToGo();
        temp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ticketToSave.setDateToGo(temp);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println(username);
            User User = userService.findUserByEmail(username);
            ticketToSave.setUser(User);
        }
        Ticket ticketToSaveInDB = ticketService.getTicketByData(temp, ticketToSave.getSeat(), ticketToSave.getWagon());
        if (id.equals(0L)) {
            if (ticketToSaveInDB == null) {
                ticketService.saveTicket(ticketToSave);
                Ticket ticket = ticketService.getTicketByData(ticketToSave.getDateToGo(), ticketToSave.getSeat(), ticketToSave.getWagon());
                return "redirect:/sendTicket/" + ticket.getId();
            } else {
                model.addAttribute("ticket", ticketToSave);
                model.addAttribute("titleTrain", TICKET_TEXT_INS);
                model.addAttribute("errorString", "Нажаль, місце вже заброньоване!");
                return "/buzz/ticket";
            }
        }
        System.out.println("------------------------------------------");
        System.out.println(ticketToSave);
        System.out.println("------------------------------------------");
        return  "redirect:/buzz";
    }

    private void authentication(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
    }

}
