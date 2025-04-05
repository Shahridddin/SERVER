package uz.pdp.simple_l.dto.book;

import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record BookCreateDto(
        UUID categoryId,
        String title,
        String description,
        MultipartFile cover,
        MultipartFile bookFile
) {
}
