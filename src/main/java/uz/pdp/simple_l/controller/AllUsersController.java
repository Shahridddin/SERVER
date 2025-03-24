package uz.pdp.simple_l.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllUsersController {

    @GetMapping("/active-users")
    public String getAllUsers() {
        return "get-all-users";
    }
}
