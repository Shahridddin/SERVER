package uz.pdp.simple_l.service.book;

import uz.pdp.simple_l.dto.book.BookCreateDto;
import uz.pdp.simple_l.entity.Category;

public interface BookService {
    Long saveBook(BookCreateDto dto, Category category);
}
