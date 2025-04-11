package uz.pdp.simple_l.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/admin/page-get")
    public String adminPage() {
        return "admin-page";
    }

    @GetMapping("/user/page-get")
    public String userPage() {
        return "user-page";
    }

    @GetMapping("/password/reset")
    public String passwordResetPage() {
        return "password-reset";
    }

    @GetMapping("/new/password")
    public String newPasswordPage() {
        return "enter-code";
    }

    @GetMapping("/book/list/page-get")
    public String bookListPage() {
        return "book-list";
    }

    @GetMapping("/exception")
    public String exceptionPage() {
        return "exception-handler";
    }
}
