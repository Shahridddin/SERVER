package uz.pdp.simple_l.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.simple_l.dto.book.BookCreateDto;
import uz.pdp.simple_l.entity.Book;
import uz.pdp.simple_l.entity.Category;
import uz.pdp.simple_l.repository.BookRepository;
import uz.pdp.simple_l.repository.CategoryRepository;
import uz.pdp.simple_l.service.book.BookCreateService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCreateService bookCreateService;

    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository, BookCreateService bookCreateService) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookCreateService = bookCreateService;
    }

    @GetMapping("/auth/category/{id}/add-book")
    public String addBookForm(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        return "book-add"; // Bu book-add.html sahifasini ochadi
    }


    @PostMapping("/auth/category/{id}/save-book")
    public String addBook(@PathVariable Long id,
                          @RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("file") MultipartFile file,
                          RedirectAttributes redirectAttributes) throws IOException {

        if (title == null || title.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Title ni kiriting!");
            return "redirect:/auth/category/add";
        }
        if (author == null || author.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Author ni kiriting!");
            return "redirect:/auth/category/add";
        }
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "File ni kiriting!");
            return "redirect:/auth/category/add";
        }

        // Kategoriya bazada borligini tekshiramiz
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kategoriya topilmadi!");
            return "redirect:/auth/category/add";
        }

        BookCreateDto dto = new BookCreateDto();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setFileData(file.getBytes());
        bookCreateService.saveBook(dto, category);

        redirectAttributes.addFlashAttribute("successMessage", "Kitob muvaffaqiyatli qo‘shildi!");
        return "redirect:/category/get-list";
    }

    @GetMapping("/auth/book/open/{id}")
    public ResponseEntity<byte[]> openFile(@RequestParam("id") Long id) {
        Optional<Category> byId = categoryRepository.findById(id);

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Category category = byId.get();
        Long userId = category.getId();
        List<Book> byCategoryId = bookRepository.findByCategory_Id(userId);
        byte[] fileData = null;
        Book book = new Book();
        for (Book books : byCategoryId) {
            if (books.getCategory().getId().equals(category.getId())) {
                book = books;
                return ResponseEntity.ok(books.getFileData());
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf"); // PDF format
        headers.add("Content-Disposition", "inline; filename=" + book.getTitle() + ".pdf");

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

}
