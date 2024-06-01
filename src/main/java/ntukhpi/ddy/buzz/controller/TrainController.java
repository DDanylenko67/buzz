package ntukhpi.ddy.buzz.controller;

import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.enums.wagonType.wagonType;
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
        String massage = cheackNumber(trainToSave);
        if(massage == null) {
            massage = checkWagon(trainToSave);
            if (massage != null) {
                model.addAttribute("train", trainToSave);
                model.addAttribute("titleTrain", TRAIN_TEXT_EDIT);
                model.addAttribute("errorString", massage);
                model.addAttribute("wagons", wagonService.getAllWagons());
                return "/admin/train";
            }
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
                if (trainToSaveInDB == null || (trainToSaveInDB != null && trainToSaveInDB.getId() == trainToSave.getId())) {
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
        else {
            model.addAttribute("train", trainToSave);
            model.addAttribute("titleTrain", TRAIN_TEXT_EDIT);
            model.addAttribute("errorString", massage);
            model.addAttribute("wagons", wagonService.getAllWagons());
            return "/admin/train";
        }
    }
    @GetMapping("/admin/trains/del/{idTrainForDelete}")
    public String DeleteTrain(Model model, @PathVariable Long idTrainForDelete){
        String messageDeleteError = "";

        Train delTrainInDB = trainService.getTrainById(idTrainForDelete);
        if (delTrainInDB != null) {
            StringBuilder sbMessageAboutPresent = new StringBuilder();
            String ticketPresent = ticketService.getInfoPresenceTicketByIDTrain(idTrainForDelete);
            if (!ticketPresent.isEmpty()) {
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
            model.addAttribute("ret_page", "/trains");
            return "/admin/crud_error";
        } else {
            return "redirect:/buzz";
        }
    }

    private String checkWagon(Train train){
        if(train.getTrainType().getDisplayName().equals(trainType.interCityPlus.getDisplayName())
                || train.getTrainType().getDisplayName().equals(trainType.interCity.getDisplayName())){
            if(train.getWagon().getWagonTypes().getDisplayName().equals(wagonType.interCityFirst.getDisplayName()) ||
                    train.getWagon().getWagonTypes().getDisplayName().equals(wagonType.interCitySecond.getDisplayName())){
                return null;
            }
            else {
                return "Потяги інтерсіті мають номер лише сидячі вагони!";
            }
        }
        else {
            if(train.getWagon().getWagonTypes().getDisplayName().equals(wagonType.interCityFirst.getDisplayName()) ||
                    train.getWagon().getWagonTypes().getDisplayName().equals(wagonType.interCitySecond.getDisplayName())){
                return "Звичайні потяги, не можуть мати вагони від інтерсіті";
            }
            return null;
        }
    }
    private String cheackNumber(Train train){
        int number = Integer.parseInt(train.getNumber());
        trainType TrainType = train.getTrainType();
        variantRuhu VariantRuhu = train.getVariantRuhu();
        if(variantRuhu.seasonSummer.getDisplayName().equals(VariantRuhu.getDisplayName()) ||
                variantRuhu.seasonDecember.getDisplayName().equals(VariantRuhu.getDisplayName()) ){
            if(number < 151 || number > 298){
                return "Сезоні поїзди мають номер від 151 до 298";
            }
        }else {
            if(trainType.fastfull.getDisplayName().equals(TrainType.getDisplayName())){
                if(number < 1 || number > 150){
                    return "Швидкі поїзди мають номер від 1 до 150";
                }
            }
            if(trainType.passenger.getDisplayName().equals(TrainType.getDisplayName())){
                if(number < 301 || number > 450){
                    return "Пасажирськи поїзди мають номер від 301 до 450";
                }
            }
            if(trainType.interCityPlus.getDisplayName().equals(TrainType.getDisplayName())){
                if(number < 751 || number > 788){
                    return "Високошвідкісні поїзди мають номер від 751 до 788";
                }
            }
            if(trainType.interCity.getDisplayName().equals(TrainType.getDisplayName())){
                if(number < 701 || number > 750){
                    return "Швидкісні поїзди мають номер від 701 до 750";
                }
            }
        }
        return null;
    }
}
