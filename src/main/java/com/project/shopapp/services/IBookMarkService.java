package com.project.shopapp.services;

import com.project.shopapp.DTO.BookmarkDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;


public interface IBookMarkService {
    ResponseEntity<?> addBookmarkToBook(Long bookID, BookmarkDTO bookmarkDTO, User user);
    ResponseEntity<?> findBookmarksByBookId(Long bookID, String username);
    ResponseEntity<?> deleteBookmarkByBookIdAndPageNumber(Long bookID, int pageNumber, User user);


}
