package com.project.shopapp.services;

import com.project.shopapp.DTO.BookDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    List<BookDTO> findAll(Map<String, Object> params);
    BookDTO createBook(BookDTO bookDTO);
    boolean deleteBookByTitle(String title);
    // Thêm các phương thức mới
    boolean sendBookRequestCheck(String title);
    boolean acceptBookRequestCheck(String title);
    List<BookDTO> getBooksWithPendingRequest();
    boolean denyBookRequestCheck(String title);

}
