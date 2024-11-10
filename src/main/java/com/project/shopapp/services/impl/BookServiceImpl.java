package com.project.shopapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.entity.BookEntity;
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
    public List<BookDTO> findAll(Map<String, Object> params, List<String> typeCode) {
        // Fetch list of BookEntity using JPA with filters
        List<BookEntity> bookEntities = bookRepository.findByParamsAndTypeCode(params, typeCode);
        List<BookDTO> result = new ArrayList<>();

        // Convert each BookEntity to BookDTO
        for (BookEntity item : bookEntities) {
            BookDTO book = new BookDTO();
            book.setbTitle(item.getTitle());
            book.setbDescription(item.getDescription());
            book.setBookID(item.getBookID());



            result.add(book);
        }

        return result;
    }
}
