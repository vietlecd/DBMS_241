package com.project.shopapp.components;

import com.project.shopapp.DTO.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookDTOFactory {
    public BookDTO createBookDTO(String title, String status) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(title);
        bookDTO.setStatus(status);
        return bookDTO;
    }
}