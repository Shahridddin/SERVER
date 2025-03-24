package uz.pdp.simple_l.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.simple_l.entity.AuthUser;
import uz.pdp.simple_l.enums.AuthRole;
import uz.pdp.simple_l.repository.AuthUserRepository;

import java.util.Optional;

@Controller
public class LoginController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*@GetMapping("/auth/login")
    public String loginPage(HttpSession session, Model model) {

        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }
        return "login";
    }

    @PostMapping("/auth/check-login")
    public String checkLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session) {

        if ((username == null || username.isEmpty()) && (password == null || password.isEmpty())) {
            session.setAttribute("errorMessage", "Iltimos, username va password kiriting!");
            return "redirect:/auth/login";
        }

        if (username == null || username.isEmpty()) {
            session.setAttribute("errorMessage", "Iltimos, username kiriting!");
            return "redirect:/auth/login";
        }

        if (password == null || password.isEmpty()) {
            session.setAttribute("errorMessage", "Iltimos, password kiriting!");
            return "redirect:/auth/login";
        }

        Optional<AuthUser> byUsername = authUserRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            session.setAttribute("errorMessage", "Foydalanuvchi topilmadi!");
            return "redirect:/auth/login";
        }

        AuthUser authUser = byUsername.get();

        if (!passwordEncoder.matches(password, authUser.getPassword())) {
            session.setAttribute("errorMessage", "Foydalanuvchi nomi yoki parol noto‘g‘ri!");
            return "redirect:/auth/login";
        }

        if (!authUser.isActive()) {
            session.setAttribute("errorMessage", "Sizning profilingiz faol emas. Iltimos, admin bilan bog‘laning!");
            return "redirect:/auth/login";
        }

        AuthRole authRole = authUser.getRole();
        session.setAttribute("successMessage", "Siz muvaffaqqiyatli ro‘yxatdan o‘tdingiz 😀😀");
        if (authRole == AuthRole.ADMIN) {
            return "admin-page";
        }
        return "user-page";
    }*/

    @GetMapping("/auth/login")
    public String login(HttpSession session, Model model) {
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage"); // Xabarni faqat bir marta ko'rsatish uchun o'chirib tashlaymiz
        }

        String successMessage = (String) session.getAttribute("successMessage");
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }

        return "login";
    }


}
