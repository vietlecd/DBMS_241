package com.project.shopapp.services;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IBookService {
    List<BookDTO> findAll(Map<String, Object> params);
    ResponseEntity<?> createBook(BookDTO bookDTO, MultipartFile pdf, MultipartFile image, User user) throws IOException;

    boolean deleteBookBybookID(Integer bookID);

    boolean acceptBookRequestCheck(Integer bookID);

    boolean denyBookRequestCheck(Integer bookID);

    ResponseEntity<?> getBookBought(String username);

    ResponseEntity<?> getBooksByAuthor(String authorName);

    ResponseEntity<?> getBookWritten(String username);
}
