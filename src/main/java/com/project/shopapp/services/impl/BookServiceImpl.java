package com.project.shopapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.BookRepository;


import com.project.shopapp.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public List<BookDTO> findAll(Map<String, Object> params) {
        // Fetch list of Book using JPA with filters
        List<Book> bookEntities = bookRepository.findByParamsAndTypeCode(params);
        List<BookDTO> result = new ArrayList<>();

        // Convert each Book to BookDTO
        for (Book item : bookEntities) {
            BookDTO book = new BookDTO();
            book.setbTitle(item.getTitle());
            book.setDescription(item.getDescription());
            book.setBookID(item.getBookID());
            book.setPrice(item.getPrice());
            book.setPublishYear(item.getPublishyear());
            book.setCoverImage(item.getCoverimage());

            // Lấy tên và mô tả của các danh mục liên quan
            Set<Category> categories = item.getCategories();
            String categoryNames = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.joining(", "));
            String categoryDescriptions = categories.stream()
                    .map(Category::getCatedescription)
                    .collect(Collectors.joining(", "));

            // Set giá trị tên và mô tả của danh mục vào BookDTO
            book.setCatedescription(categoryDescriptions);
            book.setName(categoryNames);

            result.add(book);
        }




        return result;
    }
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        // Chuyển đổi từ BookDTO sang Book
        Book book = new Book();
        book.setTitle(bookDTO.getbTitle());
        book.setDescription(bookDTO.getDescription());
        book.setCoverimage(bookDTO.getCoverImage());
        book.setPublishyear(bookDTO.getPublishYear());
        book.setPrice(bookDTO.getPrice());

        // Lưu Book vào cơ sở dữ liệu
        book = bookRepository.save(book);

        // Chuyển đổi ngược từ Book sang BookDTO để trả về
        BookDTO result = new BookDTO();
        result.setBookID(book.getBookID());
        result.setbTitle(book.getTitle());
        result.setDescription(book.getDescription());
        result.setCoverImage(book.getCoverimage());
        result.setPublishYear(book.getPublishyear());
        result.setPrice(book.getPrice());

        return result;
    }
    @Override
    public boolean deleteBookByTitle(String title) {
        // Tìm sách theo title
        Book book = bookRepository.findByTitle(title);
        if (book != null) {
            bookRepository.delete(book); // Xóa sách
            return true;
        }
        return false; // Không tìm thấy sách
    }
}


