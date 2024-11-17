package com.project.shopapp.services;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.responses.BookProjection;

import java.util.List;
import java.util.Map;

public interface IBookService {
    List<BookDTO> findAll(Map<String, Object> params);
    BookDTO createBook(BookDTO bookDTO);

    boolean deleteBookBybookID(Integer bookID);
    // Thêm các phương thức mới

    boolean acceptBookRequestCheck(Integer bookID);
   /* List<BookDTO> getBooksWithPendingRequest();*/
    boolean denyBookRequestCheck(Integer bookID);
    List<BookProjection> getBooksByAuthor(String authorUsername);



}
