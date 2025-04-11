package uz.pdp.simple_l.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.simple_l.entity.AuthUser;
import uz.pdp.simple_l.entity.Book;
import uz.pdp.simple_l.repository.AuthUserRepository;
import uz.pdp.simple_l.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AdminController {

    private final AuthUserRepository authUserRepository;
    private final BookRepository bookRepository;

    public AdminController(AuthUserRepository authUserRepository, BookRepository bookRepository) {
        this.authUserRepository = authUserRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/auth/active/users")
    public String activeUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<AuthUser> userPage = authUserRepository.findAllActiveUsers(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "get-all-users";
    }

    @PostMapping("/auth/active/block/{id}")
    public String blockUser(@PathVariable Long id) {
        Optional<AuthUser> currentUser = authUserRepository.findById(id);
        if (currentUser.isPresent()) {
            AuthUser authUser = currentUser.get();
            authUser.setActive(false);
            authUserRepository.save(authUser);
        }
        return "redirect:/auth/active/users";
    }

    @PostMapping("/auth/active/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        authUserRepository.deleteById(id);
        return "redirect:/auth/active/users";
    }


    @GetMapping("/auth/inactive/users")
    public String inActiveUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<AuthUser> userPage = authUserRepository.findAllInActiveUsers(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "inactive-users";
    }

    @PostMapping("/auth/inactive/activate/{id}")
    public String activateUser(@PathVariable Long id) {
        Optional<AuthUser> currentUser = authUserRepository.findById(id);
        if (currentUser.isPresent()) {
            AuthUser authUser = currentUser.get();
            authUser.setActive(true);
            authUserRepository.save(authUser);
        }
        return "redirect:/auth/inactive/users";
    }

    @PostMapping("/auth/inactive/delete/{id}")
    public String deleteInactiveUser(@PathVariable Long id) {
        authUserRepository.deleteById(id);
        return "redirect:/auth/inactive/users";
    }

    @GetMapping("/book/get-list")
    public String bookList(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        model.addAttribute("books", bookPage);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "book-list";
    }

}
