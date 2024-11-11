package com.project.shopapp.controllers;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/api/book")
    public List<BookDTO> getBook(
            @RequestParam Map<String, Object> params) {

        // Gọi đến service để lấy danh sách BookDTO dựa trên tham số truyền vào
        List<BookDTO> result = bookService.findAll(params);
        return result;
    }

}
