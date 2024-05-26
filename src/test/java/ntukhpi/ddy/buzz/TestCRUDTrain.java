package ntukhpi.ddy.buzz;

import ntukhpi.ddy.buzz.entity.Role;
import ntukhpi.ddy.buzz.entity.Train;
import ntukhpi.ddy.buzz.entity.User;
import ntukhpi.ddy.buzz.entity.Wagon;
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
        wagonService.saveWagon(new Wagon("Кировоградський завод"));
        Train train = new Train("126");
        train.setPointDo("Дніпро");
        trainService.saveTrain(train);
        Train train1 = new Train("623");
        train1.setPointDo("Львів");
        trainService.saveTrain(train1);
        Train train2 = new Train("156");
        train2.setPointDo("Одеса");
        trainService.saveTrain(train2);
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
