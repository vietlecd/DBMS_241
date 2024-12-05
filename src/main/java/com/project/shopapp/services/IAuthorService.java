package com.project.shopapp.services;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.AuthorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAuthorService {

    AuthorResponse infoAuthor(User user);
    void becomeAuthor(User user, AuthorDTO authorDTO) throws InvalidParamException;
    ResponseEntity<String> acceptedAuthor(String username);
    ResponseEntity<String> deniedAuthor(String username);
    ResponseEntity<String> deleteAuthor(String username);
    List<AuthorResponse> getAuThor();

    List<Author> getAuthorByName(String authorName);

    List<AuthorResponse> getAuthorRequest();



}
