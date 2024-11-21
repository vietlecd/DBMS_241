package com.project.shopapp.services;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.BookProjection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IBookService {
    List<BookDTO> findAll(Map<String, Object> params);
    ResponseEntity<?> createBook(BookDTO bookDTO, MultipartFile pdf);

    boolean deleteBookBybookID(Integer bookID);
    // Thêm các phương thức mới

    boolean acceptBookRequestCheck(Integer bookID);
   /* List<BookDTO> getBooksWithPendingRequest();*/
    boolean denyBookRequestCheck(Integer bookID);
    ResponseEntity<?> getBooksByAuthor(String authorUsername);

    ResponseEntity<?> getBookBought(User user);



}
