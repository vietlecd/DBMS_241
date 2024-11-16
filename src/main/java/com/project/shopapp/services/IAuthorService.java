package com.project.shopapp.services;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.BaseProjection;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAuthorService {
    ResponseEntity<String> becomeAuthor(User user, AuthorDTO authorDTO);

    ResponseEntity<String> acceptedAuthor(String username);

    ResponseEntity<String> deniedAuthor(String username);

    ResponseEntity<String> deleteAuthor(String username);

    List<BaseProjection> getAuThor();

    List<BaseProjection> getAuthorRequest();



}
