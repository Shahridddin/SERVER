package uz.pdp.simple_l.service.category;

import uz.pdp.simple_l.dto.category.CategoryCreateDto;

public interface CategoryService {
    Long createCategory(CategoryCreateDto dto);
}
