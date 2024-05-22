package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    Ticket saveTicket(Ticket ticket);
    Ticket getTicketById(Long id);
    Ticket updateTicket(Long id, Ticket ticket);
    void deleteTicketById(Long id);
    Ticket getTicketByData(LocalDate localDate, int seat, int wagon);
    Ticket getTicketByOwner(String name);
    String getInfoPresenceTicketByIDTrain(Long idTrain);
}
