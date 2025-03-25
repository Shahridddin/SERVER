package uz.pdp.simple_l.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
    String title;
    String author;
    byte[] fileData;
}
