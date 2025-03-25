package uz.pdp.simple_l.service.book;

import org.springframework.stereotype.Service;
import uz.pdp.simple_l.dto.book.BookCreateDto;
import uz.pdp.simple_l.entity.Book;
import uz.pdp.simple_l.entity.Category;
import uz.pdp.simple_l.repository.BookRepository;

@Service
public class BookCreateService implements BookService {

    private final BookRepository bookRepository;

    public BookCreateService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Long saveBook(BookCreateDto dto, Category category) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setFileData(dto.getFileData());
        book.setCategory(category);
        bookRepository.save(book);
        return book.getId();
    }
}
