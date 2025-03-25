package uz.pdp.simple_l.service.category;

import org.springframework.stereotype.Service;
import uz.pdp.simple_l.dto.category.CategoryCreateDto;
import uz.pdp.simple_l.entity.Category;
import uz.pdp.simple_l.repository.CategoryRepository;

@Service
public class CategoryCreateService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryCreateService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long createCategory(CategoryCreateDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        categoryRepository.save(category);
        return category.getId();
    }
}
