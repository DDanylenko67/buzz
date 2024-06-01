package ntukhpi.ddy.buzz.service;

import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.entity.User;

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
    int findByTicketsByDate(Long idTrain, LocalDate date);
    List<Ticket> findTickets(Long idTrain, LocalDate date);
    int getSize(Long idTrain);
    List<Ticket> findTickets(User user);
}
