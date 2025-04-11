package uz.pdp.simple_l.service.book;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.pdp.simple_l.dto.book.BookCreateDto;
import uz.pdp.simple_l.entity.Book;
import uz.pdp.simple_l.repository.BookRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class BookService implements BookServiceInterface {

    private final BookRepository bookRepository;
    private final Path rootPath = Path.of("C:\\Deplom ishi\\Simple_L\\files");

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String create(BookCreateDto dto, UUID categoryId) throws IOException {
        Book book = new Book();
        String bookFileOriginalName = dto.bookFile().getOriginalFilename();
        String coverFileOriginalName = dto.coverFile().getOriginalFilename();
        String bookFileGeneratedName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(bookFileOriginalName);
        String coverFileGeneratedName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(coverFileOriginalName);

        book.setName(dto.name());
        book.setDescription(dto.description());
        book.setFileOriginalName(bookFileOriginalName);
        book.setFileGeneratedName(bookFileGeneratedName);
        book.setCoverFileOriginalName(coverFileOriginalName);
        book.setCoverFileGeneratedName(coverFileGeneratedName);
        book.setFileSize(dto.bookFile().getSize());
        book.setCoverFileSize(dto.coverFile().getSize());
        book.setFileContentType(dto.bookFile().getContentType());
        book.setCoverFileContentType(dto.coverFile().getContentType());
        book.setCategoryId(categoryId);
        bookRepository.save(book);

        Files.copy(dto.bookFile().getInputStream(), rootPath.resolve(bookFileGeneratedName), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(dto.coverFile().getInputStream(), rootPath.resolve(coverFileGeneratedName), StandardCopyOption.REPLACE_EXISTING);

        return bookFileOriginalName + ":" + coverFileOriginalName;
    }
}
