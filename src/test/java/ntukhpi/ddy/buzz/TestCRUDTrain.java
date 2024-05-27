package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Role;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.entity.User;
import ntukhpi.ddy.buzz.entity.Wagon;
import ntukhpi.ddy.buzz.enums.trainType.trainType;
import ntukhpi.ddy.buzz.enums.variantRuhu.variantRuhu;
import ntukhpi.ddy.buzz.repository.RoleRepository;
import ntukhpi.ddy.buzz.repository.TicketRepository;
import ntukhpi.ddy.buzz.repository.TrainRepository;
import ntukhpi.ddy.buzz.repository.UserRepository;
import ntukhpi.ddy.buzz.service.TicketService;
import ntukhpi.ddy.buzz.service.TrainService;
import ntukhpi.ddy.buzz.service.UserService;
import ntukhpi.ddy.buzz.service.WagonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestCRUDTrain {
    @Autowired
    private TrainService trainService;
    @Autowired
    private WagonService wagonService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Test
    void roleTest(){
        User user =  userService.findUserByEmail("admin@gmail.com");
        Role role = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }
    @Test
    void addTrains(){
        Train train = new Train("711", trainType.interCity.getDisplayName(), wagonService.getWagonById(10L), "Київ-Пасажирський",
                "Харків-Пасажирський", variantRuhu.daily.getDisplayName(), "10:50", "15:55",483);
        Train train1 = new Train("751", trainType.interCityPlus.getDisplayName(), wagonService.getWagonById(10L), "Київ-Пасажирський",
                "Харків-Пасажирський", variantRuhu.daily.getDisplayName(), "11:35", "16:20",483);
        Train train2 = new Train("350", trainType.passenger.getDisplayName(), wagonService.getWagonById(10L), "Київ-Пасажирський",
                "Харків-Пасажирський", variantRuhu.daily.getDisplayName(), "07:55", "14:55",483);
        Train train3 = new Train("123", trainType.fastfull.getDisplayName(), wagonService.getWagonById(10L), "Київ-Пасажирський",
                "Харків-Пасажирський", variantRuhu.daily.getDisplayName(), "09:25", "15:35",483);
        trainService.saveTrain(train);
        trainService.saveTrain(train1);
        trainService.saveTrain(train2);
        trainService.saveTrain(train3);

    }

    @Test
    void getALl(){
        System.out.println(trainService.getAllTrains());
    }
    @Test
    void getTrainByID(){
        System.out.println(trainService.getTrainById(1L));
    }
    @Test
    void updateTrainByID(){
        Train temp = trainService.getTrainById(1L);
        System.out.println(temp);
        temp.setDistance(600);
        temp.setVariantRuhu(variantRuhu.daily);
        trainService.updateTrain(1L, temp);
        System.out.println(trainService.getTrainById(1L));
    }
    @Test
    void getTrainByNumber(){
        System.out.println(trainService.getTrainByNumber("710"));
    }
    @Test
    void deleteTrainByID(){
        trainService.saveTrain(new Train("700"));
        trainService.deleteTrainById(4L);
    }

}
