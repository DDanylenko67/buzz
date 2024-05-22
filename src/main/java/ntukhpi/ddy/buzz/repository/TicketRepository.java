package ntukhpi.ddy.buzz.repository;

import ntukhpi.ddy.buzz.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByDateToGoAndWagonAndSeat(LocalDate date, int wagon, int seat);
    Ticket findByOwner(String name);
    List<Ticket> findTicketsByTrainId(Long idTrain);
}
