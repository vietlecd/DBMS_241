package com.project.shopapp.services;

import com.project.shopapp.DTO.AuthorDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthorService {
    ResponseEntity<String> becomeAuthor(String myUsername, AuthorDTO authorDTO);

    ResponseEntity<String> acceptedAuthor(String username);

    ResponseEntity<String> deniedAuthor(String username);
}
