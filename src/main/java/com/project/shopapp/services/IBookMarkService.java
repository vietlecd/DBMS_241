package com.project.shopapp.services;

import com.project.shopapp.DTO.BookmarkDTO;

import com.project.shopapp.DTO.ReviewDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IBookMarkService {
    ResponseEntity<?> addBookmarkToBook(Long bookID, BookmarkDTO bookmarkDTO, User user);
    ResponseEntity<?> findBookmarksByBookId(Long bookID, String username);
    ResponseEntity<?> deleteBookmarkByBookIdAndPageNumber(Long bookID, int pageNumber, User user);


}
