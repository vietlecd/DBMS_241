package com.project.shopapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.BookEntity;
import com.project.shopapp.models.CategoryEntity;
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
        // Fetch list of BookEntity using JPA with filters
        List<BookEntity> bookEntities = bookRepository.findByParamsAndTypeCode(params);
        List<BookDTO> result = new ArrayList<>();

        // Convert each BookEntity to BookDTO
        for (BookEntity item : bookEntities) {
            BookDTO book = new BookDTO();
            book.setbTitle(item.getTitle());
            book.setDescription(item.getDescription());
            book.setBookID(item.getBookID());
            book.setPrice(item.getPrice());
            book.setPublishYear(item.getPublishyear());
            book.setCoverImage(item.getCoverimage());

            // Lấy tên và mô tả của các danh mục liên quan
            Set<CategoryEntity> categories = item.getCategories();
            String categoryNames = categories.stream()
                    .map(CategoryEntity::getName)
                    .collect(Collectors.joining(", "));
            String categoryDescriptions = categories.stream()
                    .map(CategoryEntity::getCatedescription)
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
        // Chuyển đổi từ BookDTO sang BookEntity
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDTO.getbTitle());
        bookEntity.setDescription(bookDTO.getDescription());
        bookEntity.setCoverimage(bookDTO.getCoverImage());
        bookEntity.setPublishyear(bookDTO.getPublishYear());
        bookEntity.setPrice(bookDTO.getPrice());

        // Lưu BookEntity vào cơ sở dữ liệu
        bookEntity = bookRepository.save(bookEntity);

        // Chuyển đổi ngược từ BookEntity sang BookDTO để trả về
        BookDTO result = new BookDTO();
        result.setBookID(bookEntity.getBookID());
        result.setbTitle(bookEntity.getTitle());
        result.setDescription(bookEntity.getDescription());
        result.setCoverImage(bookEntity.getCoverimage());
        result.setPublishYear(bookEntity.getPublishyear());
        result.setPrice(bookEntity.getPrice());

        return result;
    }
    @Override
    public boolean deleteBookByTitle(String title) {
        // Tìm sách theo title
        BookEntity bookEntity = bookRepository.findByTitle(title);
        if (bookEntity != null) {
            bookRepository.delete(bookEntity); // Xóa sách
            return true;
        }
        return false; // Không tìm thấy sách
    }
}


