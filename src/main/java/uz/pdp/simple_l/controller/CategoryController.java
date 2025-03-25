package uz.pdp.simple_l.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.simple_l.dto.category.CategoryCreateDto;
import uz.pdp.simple_l.entity.Category;
import uz.pdp.simple_l.repository.CategoryRepository;
import uz.pdp.simple_l.service.category.CategoryCreateService;

@Controller
public class CategoryController {

    private final CategoryCreateService categoryCreateService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryCreateService categoryCreateService, CategoryRepository categoryRepository) {
        this.categoryCreateService = categoryCreateService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category/get/page")
    public String category() {
        return "category-page";
    }

    @PostMapping("/create/category")
    public String createCategory(@ModelAttribute CategoryCreateDto dto,
                                 RedirectAttributes redirectAttributes) {
        if (dto.name() == null || dto.name().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Iltimos category nomini kiriting!");
            return "redirect:/category/get/page";
        }
        categoryCreateService.createCategory(dto);
        return "admin-page";
    }

    @GetMapping("/category/get-list")
    public String activeUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Category> categoryPage = categoryRepository.findAllCategories(pageable);

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("currentPage", page);

        return "category-list";
    }

}
