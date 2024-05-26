package ntukhpi.ddy.buzz.service.impl;

import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.repository.TicketRepository;
import ntukhpi.ddy.buzz.repository.TrainRepository;
import ntukhpi.ddy.buzz.service.TicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    public TicketServiceImpl (TicketRepository ticketRepository, TrainRepository trainRepository){
        super();
        this.ticketRepository = ticketRepository;
    }
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket updateTicket(Long id, Ticket ticket) {
        ticket.setId(id);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketById(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket getTicketByData(LocalDate localDate, int seat, int wagon) {
        return ticketRepository.findByDateToGoAndWagonAndSeat(localDate, wagon, seat);
    }

    @Override
    public Ticket getTicketByOwner(String name) {
        return ticketRepository.findByOwner(name);
    }

    @Override
    public String getInfoPresenceTicketByIDTrain(Long idTrain) {
        List<Ticket> list =  ticketRepository.findTicketsByTrainId(idTrain);
        return list.isEmpty()?"":"Є дані про квиток на потяг у БД!";
    }
}
