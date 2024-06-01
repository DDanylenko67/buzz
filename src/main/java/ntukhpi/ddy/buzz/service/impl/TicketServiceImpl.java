package ntukhpi.ddy.buzz.service.impl;

import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.User;
import ntukhpi.ddy.buzz.repository.TicketRepository;
import ntukhpi.ddy.buzz.repository.TrainRepository;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TrainService trainService;
    public TicketServiceImpl (TicketRepository ticketRepository, TrainService trainService){
        super();
        this.ticketRepository = ticketRepository;
        this.trainService = trainService;
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

    @Override
    public int findByTicketsByDate(Long idTrain, LocalDate date) {
        int count = trainService.getTrainById(idTrain).getWagon().getNumberOfSeats()*10;

        return count - ticketRepository.findByTrainIdAndDateToGo(idTrain, date).size() ;
    }
    @Override
    public int getSize(Long idTrain) {
        int count = trainService.getTrainById(idTrain).getWagon().getNumberOfSeats()*10;
        return count;
    }

    @Override
    public List<Ticket> findTickets(Long idTrain, LocalDate date) {
        return ticketRepository.findByTrainIdAndDateToGo(idTrain, date);
    }

    @Override
    public List<Ticket> findTickets(User user) {
        return ticketRepository.findByUser(user);
    }
}
