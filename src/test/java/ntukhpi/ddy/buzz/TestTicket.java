package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Tariff;
import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
import ntukhpi.ddy.buzz.service.TariffService;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import ntukhpi.ddy.buzz.service.WagonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class TestTicket {
    @Autowired
    private TrainService trainService;
    @Autowired
    private WagonService wagonService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TariffService tariffService;

    @Test
    void addTickets(){
        Ticket ticket = new Ticket(trainService.getTrainById(1L), "Денис Даниленко Юрійович", "13161231",5, 5, "2024-05-25", 600);
        ticketService.saveTicket(ticket);
        Ticket ticket1 = new Ticket(trainService.getTrainById(1L), "Кирил Буряк Сергійович", "13312354",5, 6, "2024-05-25", 600);
        ticketService.saveTicket(ticket1);
        Ticket ticket2 = new Ticket(trainService.getTrainById(1L), "Валентин Острозький Кирилович", "131231256",5, 7, "2024-05-25", 600);
        ticketService.saveTicket(ticket2);
    }

    @Test
    void getAllTickets(){
        System.out.println(ticketService.getAllTickets());
    }

    @Test
    void CRUDTest(){
        Ticket ticket = new Ticket(trainService.getTrainById(1L), "Никита Буряк Сергійович", "13161231",5, 8, "2024-05-25", 600);
        System.out.println(ticket);
        ticketService.saveTicket(ticket);
        Ticket temp = ticketService.getTicketByData(LocalDate.of(2024, 5, 25), 8, 5);
        temp.setSeat(10);
        temp.setDocumentID("12389016");
        System.out.println(temp);
        ticketService.updateTicket(temp.getId(), ticket);
        ticketService.deleteTicketById(temp.getId());
    }
    @Test
    void calcPriceTest(){
        Ticket ticket = ticketService.getTicketById(1L);
        System.out.println(ticket);
        ticket.setDateToGo(LocalDate.of(2024, 12, 31));
        ticket.getTrain().setTrainType(trainType.passenger);
        ticket.getTrain().getWagon().setWagonTypes(wagonType.plackart);
        Tariff tariff = tariffService.getTariffByTypes(ticket.getTrain().getWagon().getWagonTypes(), ticket.getTrain().getTrainType());
        System.out.println(ticket.getTrain());
        System.out.println(tariff);
        double sum = tariff.getBasePrice();
        for (int i = 0; i < ticket.getTrain().getDistance(); i+=10){
            sum += tariff.getDecadeSum();
        }
        System.out.println(sum*1.07*1*3);
        System.out.println(tariff.compPrice(ticket));
    }
}
