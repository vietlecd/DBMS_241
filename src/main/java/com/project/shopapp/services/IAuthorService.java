package com.project.shopapp.services;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.models.Author;
import com.project.shopapp.responses.AuthorDTOResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAuthorService {
    ResponseEntity<String> becomeAuthor(String myUsername, AuthorDTO authorDTO);

    ResponseEntity<String> acceptedAuthor(String username);

    ResponseEntity<String> deniedAuthor(String username);

    ResponseEntity<String> deleteAuthor(String username);

    List<AuthorDTOResponse> getAuThor();

    List<AuthorDTOResponse> getAuthorRequest();
}
