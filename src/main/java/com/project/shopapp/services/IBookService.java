package com.project.shopapp.services;

import com.project.shopapp.DTO.BookDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    List<BookDTO> findAll(Map<String, Object> params);
}
