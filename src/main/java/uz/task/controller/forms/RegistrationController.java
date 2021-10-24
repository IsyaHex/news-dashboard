package uz.task.controller.forms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.task.dto.UserSaveDto;
import uz.task.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private static final String urlLogin = "http://localhost:5665/login";
    private static final String urlRegister = "http://localhost:5665/register";

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("user", new UserSaveDto());
        return "_forms/register";
    }

    @GetMapping("/login")
    public String goBack() {
        return "redirect:_forms/login";
    }

    @PostMapping("/newUser")
    public String addUser(@Valid UserSaveDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "redirect:_forms/register";
        }
        userService.save(user);
        return "redirect:" + urlLogin;

//        try {
//            userService.save(user);
//        } catch (UserAlreadyExistException e) {
//            bindingResult.rejectValue("username", "user.username", "An account already exists for this email.");
//            model.addAttribute("user", user);
//            return "redirect:_forms/register";
//        }
//        return "redirect:_forms/login";
    }
}
