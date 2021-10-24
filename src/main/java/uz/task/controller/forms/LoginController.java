package uz.task.controller.forms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login() {
        return "_forms/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register() {
        return "redirect:_forms/register";
    }
}
