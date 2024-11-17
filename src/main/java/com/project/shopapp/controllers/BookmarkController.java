package com.project.shopapp.controllers;

import com.project.shopapp.DTO.BookmarkDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;


import com.project.shopapp.services.IBookMarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/bookmark")
public class BookmarkController {

    @Autowired
    IBookMarkService bookmarkService;
    @Autowired
    private AuthenticationHelper authenticationHelper;

    // Endpoint to add a bookmark to a book
    @PostMapping("/add/{bookID}")
    public ResponseEntity<?> addBookmarkToBook(
            @PathVariable Long bookID,
            @RequestBody BookmarkDTO bookmarkDTO, Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);

        return bookmarkService.addBookmarkToBook(bookID, bookmarkDTO, user);
    }

    // Endpoint to find all bookmarks by book ID
/*    @GetMapping("/get/{bookID}")
    public List<BookmarkDTO> getBookmarksByBookId(@PathVariable Long bookID, Authentication authentication) {
        String username = authenticationHelper.getUsername(authentication);
        return bookmarkService.findBookmarksByBookId(bookID, username);
    }

    // Endpoint to update a bookmark
    @PutMapping("/update/{bookmarkID}")
    public BookmarkDTO updateBookmark(
            @PathVariable Long bookmarkID,
            @RequestBody BookmarkDTO bookmarkDTO) {
        return bookmarkService.updateBookmark(bookmarkID, bookmarkDTO);*/
    }

