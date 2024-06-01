package ntukhpi.ddy.buzz.controller;

import ntukhpi.ddy.buzz.entity.EmailDetails;
import ntukhpi.ddy.buzz.entity.Ticket;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.service.EmailService;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;
    private final TrainService trainService;
    private final TicketService ticketService;
    @Autowired
    public EmailController(TrainService trainService, TicketService ticketService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
    }
    @GetMapping("/sendReport/{idTrain}")
    public String sendReport(@PathVariable Long idTrain,
                             @RequestParam(name = "timeToGos") LocalDate timeToGos,
                             @RequestParam(name = "email") String email,
                             Model model) {
        timeToGos.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int y = ticketService.getSize(idTrain);
        List<Ticket> tickets = ticketService.findTickets(idTrain, timeToGos);
        double price = 0;
        for(Ticket ticket : tickets){
            price += ticket.getPrice();
        }
        String massage;
        if ((y - tickets.size()) == 0){
            massage = "Не було проданого жодного квитка \n" + "Було зароблено: " + price;;
        }else {
            massage = "Залишилося квитків" + (y-tickets.size()) + " квитів \n" + "Було зароблено: " + price;
        }
        EmailDetails details = new EmailDetails();
        details.setRecipient(email);
        details.setSubject("Звіт продажів");
        details.setMsgBody(massage);
        String status
                = emailService.sendSimpleMail(details);
        System.out.println(status);
        return "redirect:/buzz";
    }

    @GetMapping("/sendTicket/{ticketId}")
    public String sendMailWithAttachment(@PathVariable Long ticketId)
    {
        EmailDetails details = new EmailDetails();
        Ticket ticket = ticketService.getTicketById(ticketId);
        details.setRecipient(ticket.getEmail());
        details.setSubject("Квиток");
        details.setMsgBody("Електроний квиток.");
        details.setAttachment(saveToFile(ticket));
        String status
                = emailService.sendMailWithAttachment(details);
        System.out.println(status);
        return "redirect:/buzz";
    }

    public String saveToFile(Ticket ticket) {
        String path = "C:\\Users\\deadt\\OneDrive\\Рабочий стол\\КР\\Project\\buzz\\src\\main\\resources\\ticket.html";
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"))) {
            writeHeader(writer, "Електроний квиток", new Date(),  "Посадочний квиток:");
            writeComponents(writer, ticket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
    private void writeHeader(PrintWriter writer, String title, Date date, String otherInfo) {
        writer.println("<html>");
        writer.println("<head><title>" + title + "</title> ");
        writer.println("<style type=\"text/css\">\n" +
                "    content {\n" +
                "        max-width: 1000px;\n" +
                "        margin: auto;\n" +
                "    }\n" +
                "\n" +
                "    TABLE {\n" +
                "        width: 100%; /* Ширина таблицы */\n" +
                "        border: 2px solid black; /* Рамка вокруг таблицы */\n" +
                "    }\n" +
                "\n" +
                "    TD, TH {\n" +
                "        padding: 3px; /* Поля вокруг содержимого ячеек */\n" +
                "        border: 2px solid black; /* Рамка вокруг таблицы */\n" +
                "    }\n" +
                "\n" +
                "    TH {\n" +
                "        text-align: left; /* Выравнивание по левому краю */\n" +
                "        background: white ; /* Цвет фона */\n" +
                "        color: #3f48cc; /* Цвет текста */\n" +
                "    }\n" +
                "</style>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>" + title + "</h1>");
        writer.println("<p>Дата: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "</p>");
        writer.println("<p>" + otherInfo + "</p>");
    }


    public void writeComponents(PrintWriter writer, Ticket ticket) {
        int temp = 0;
        writer.println("<table>" +
                "    <tr>" +
                "        <th>МПС</th>\n" +
                "        <th colspan=\"2\">Цей посадочний документ є підставою для проїзду</th>\n" +
                "        <th>"+ ticket.getDateToGo() + "</th>\n" +
                "    </tr>");
            writer.println(" <tr>" +
                    "        <td>" + "Приз. Ім'я "+ "</td>\n" +
                    "        <td>" + ticket.getOwner() + "</td>\n" +
                    "        <td>" + "Поїзд:" + "</td>\n" +
                    "        <td>" + ticket.getTrain().getNumber() + "</td>\n" +
                    "        </tr>");
        writer.println(" <tr>" +
                "        <td>" + "Станція відправлення: "+ "</td>\n" +
                "        <td>" + ticket.getTrain().getPointVid() + "</td>\n" +
                "        <td>" + "Вагон: " + "</td>\n" +
                "        <td>" + ticket.getWagon() + "</td>\n" +
                "        </tr>");
        writer.println(" <tr>" +
                "        <td>" + "Станція Прибуття: "+ "</td>\n" +
                "        <td>" + ticket.getTrain().getPointDo() + "</td>\n" +
                "        <td>" + "Місце:" + "</td>\n" +
                "        <td>" + ticket.getSeat() + "</td>\n" +
                "        </tr>");
        writer.println(" <tr>" +
                "        <td>" + "Дата/час відп."+ "</td>\n" +
                "        <td>" + ticket.getTrain().getTimeToGo() + "</td>\n" +
                "        <td>" + "Cервіс: " + "</td>\n" +
                "        <td>" +  "</td>\n" +
                "        </tr>");
        writer.println(" <tr>" +
                "        <td>" + "Дата/час приб. "+ "</td>\n" +
                "        <td>" +  ticket.getTrain().getTimeToArrive() + "</td>\n" +
                "        <td>" + "</td>\n" +
                "        <td>" +  "</td>\n" +
                "        </tr>");
        writer.println("</table>");
    }
}
