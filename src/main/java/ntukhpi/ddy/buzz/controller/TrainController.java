package ntukhpi.ddy.buzz.controller;

import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import ntukhpi.ddy.buzz.service.WagonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Controller
public class TrainController {
    private final TrainService trainService;
    private final TicketService ticketService;
    private final WagonService wagonService;

    private final String TRAIN_TEXT_INS = "Новий потяг";
    private final String TRAIN_TEXT_EDIT = "Редагування потягу";
    @Autowired
    public TrainController(TrainService trainService, TicketService ticketService, WagonService wagonService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
        this.wagonService = wagonService;
    }
    @GetMapping("/admin/trains/new")
    public String CreateTrain(Model model){
        Train newTrain = new Train("", trainType.passenger.getDisplayName(), wagonService.getWagonById(1L), "", "", variantRuhu.daily.toString(), "00:00", "00:00",  0);
        model.addAttribute("train", newTrain);
        model.addAttribute("titleTrain", TRAIN_TEXT_INS);
        model.addAttribute("errorString", null);
        model.addAttribute("wagons", wagonService.getAllWagons());
        return "/admin/train";
    }

    @GetMapping("/admin/trains/edit/{idEdit}")
    public String EditTrain(@PathVariable Long idEdit, Model model){
        Train trainForUpdateInDB = trainService.getTrainById(idEdit);
        model.addAttribute("train", trainForUpdateInDB);
        System.out.println(trainForUpdateInDB);
        model.addAttribute("titleTrain", TRAIN_TEXT_EDIT);
        model.addAttribute("wagons", wagonService.getAllWagons());
        model.addAttribute("errorString", null);
        return "/admin/train";
    }
    @PostMapping("/admin/trains/save/{id}")
    public String SaveTrain(Model model, @PathVariable Long id,
                            @ModelAttribute("train") Train trainToSave){
        LocalTime temp = trainToSave.getTimeToGo();
        LocalTime tempDur = trainToSave.getDurationTime();
        temp = temp.plusHours(tempDur.getHour());
        temp = temp.plusMinutes(tempDur.getMinute());
        temp.format(DateTimeFormatter.ofPattern("HH:mm"));
        trainToSave.setTimeToArrive(temp);
        Train trainToSaveInDB = trainService.getTrainByNumber(trainToSave.getNumber());
        if (id.equals(0L)) {
            if (trainToSaveInDB == null) {
                trainService.saveTrain(trainToSave);
                return "redirect:/admin";
            } else {
                model.addAttribute("train", trainToSave);
                model.addAttribute("titleTrain", TRAIN_TEXT_INS);
                model.addAttribute("wagons", wagonService.getAllWagons());
                model.addAttribute("errorString", "Потяг за таким номер вже існує!");
                return "/admin/train";
            }
        } else {
            if ( trainToSaveInDB == null || (trainToSaveInDB != null && trainToSaveInDB.getId() == trainToSave.getId())) {
                Train existingTrain = trainService.getTrainById(id);
                if (existingTrain == null) {
                    model.addAttribute("train", trainToSave);
                    model.addAttribute("titleTrain", TRAIN_TEXT_EDIT);
                    model.addAttribute("errorString", "Потяг, який потрібно обновити не знайдено в базі даних!");
                    model.addAttribute("wagons", wagonService.getAllWagons());
                    return "/admin/train";
                } else {
                    trainService.updateTrain(existingTrain.getId(), trainToSave);
                    return "redirect:/admin";
                }
            } else {
                model.addAttribute("train", trainToSave);
                model.addAttribute("titleTrain", TRAIN_TEXT_EDIT);
                model.addAttribute("wagons", wagonService.getAllWagons());
                model.addAttribute("errorString", "Потяг за таким номер вже існує!");
                return "/admin/train";
            }
        }
    }
    @GetMapping("/admin/trains/del/{idTrainForDelete}")
    public String DeleteTrain(Model model, @PathVariable Long idTrainForDelete){
        String messageDeleteError = "";
        Train delTrainInDB = trainService.getTrainById(idTrainForDelete);
        if (delTrainInDB != null) {
            StringBuilder sbMessageAboutPresent = new StringBuilder();
            String ticketPresent = ticketService.getInfoPresenceTicketByIDTrain(idTrainForDelete);
            if (ticketPresent != null)   {
                sbMessageAboutPresent.append("<br><br>").append(ticketPresent);
            }
            if (sbMessageAboutPresent.toString().isEmpty()) {
                trainService.deleteTrainById(idTrainForDelete);
            } else {
                messageDeleteError = "Object: TRAIN, id=" + idTrainForDelete + sbMessageAboutPresent;
            }
        } else {
            messageDeleteError = "Object: TRAIN, id=" + idTrainForDelete
                    + "<br><br>Такого потяга немає у БД!";
        }
        if (!messageDeleteError.isEmpty()) {
            model.addAttribute("error_del_message", messageDeleteError);
            model.addAttribute("ret_page", "/admin");
            return "/admin/crud_error";
        } else {
            return "redirect:/admin";
        }
    }
    @GetMapping("/admin/trains/report/{id}")
    public String ReportTrain(Model model, @PathVariable Long id){
        return "/admin/admin";
    }

}