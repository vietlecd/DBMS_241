package com.project.shopapp.services;

import com.project.shopapp.DTO.BookmarkDTO;

import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface IBookMarkService {
    ResponseEntity<?> addBookmarkToBook(Long bookID, BookmarkDTO bookmarkDTO, User user);
}
