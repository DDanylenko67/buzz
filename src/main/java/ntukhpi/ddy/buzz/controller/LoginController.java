package ntukhpi.ddy.buzz.controller;

import jakarta.validation.Valid;
import ntukhpi.ddy.buzz.entity.User;
import ntukhpi.ddy.buzz.service.UserDto;
import ntukhpi.ddy.buzz.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "/login/login";
    }
    @GetMapping("/signUp")
    public String signUp(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/login/signup";
    }
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "Така почта вже зареєстрована");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/login/signup";
        }
        userService.saveUser(userDto);
        return "redirect:/login?success";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/buzz";
    }

    @GetMapping("/?continue")
    public String homes() {
        return "redirect:/buzz";
    }
}
