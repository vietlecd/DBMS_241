package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.models.Author;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.AuthorRepositoryCustom;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.AuthorResponse;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthorService implements IAuthorService {
    private UserRepository userRepository;
    private AuthorRepository authorRepository;
    private AuthorRepositoryCustom authorRepositoryCustom;

    @Override
    public AuthorResponse infoAuthor(User user) {
        Author author = authorRepository.findAuthorByUser(user);
        if (author != null) {
            AuthorResponse res = AuthorResponse.builder()
                    .id_card(author.getIdCard())
                    .bio(author.getBio())
                    .fullname(author.getUser().getFullName())
                    .username(author.getUser().getUsername())
                    .phone_number(author.getUser().getPhoneNumber())
                    .build();
            return res;
        } else {
            throw new DataNotFoundException("Author not found");
        }
    }

    @Override
    public void becomeAuthor(User user, AuthorDTO authorDTO) throws InvalidParamException {
        if (!authorRepository.existsAuthorByUser(user)) {
            Author author = Author.builder()
                    .user(user)
                    .bio(authorDTO.getBio())
                    .idCard(authorDTO.getId_card())
                    .status(0)
                    .build();
            authorRepository.save(author);
        }
        else
           throw new InvalidParamException("Author has been existed");
    }
    @Override
    public ResponseEntity<String> acceptedAuthor(String username) {
        Author author = authorRepositoryCustom.getAuthorByUsernameAndStatus(username, 0);
        if (author != null && author.getStatus() == 0) {;
            author.setStatus(1);
            authorRepository.save(author);
        } else {
            throw new DataNotFoundException("Author not found" + username);
        }

        return new ResponseEntity<>("Add author successfully", HttpStatus.ACCEPTED);
    }
//
    @Override
    public ResponseEntity<String> deniedAuthor(String username) {
        Author author = authorRepositoryCustom.getAuthorByUsernameAndStatus(username, 0);
        if (author != null && author.getStatus() == 0) {
            author.setStatus(-1);
            authorRepository.save(author);

        } else {
            throw new DataNotFoundException("Author not found" + username);
        }

        return new ResponseEntity<>("Deny author successfully", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deleteAuthor(String username) {
        Author author = authorRepositoryCustom.getAuthorByUsernameAndStatus(username, 1);
        if (author==null) {
            throw new DataNotFoundException("Author not found" + username);
        }
        Role authorRole = new Role();
        authorRole.setId(2);

        User user1 = author.getUser();
        user1.setRole(authorRole);

        userRepository.save(user1);

        authorRepository.delete(author);

        return new ResponseEntity<>("Delete author successfully", HttpStatus.ACCEPTED);
    }

    @Override
    public List<AuthorResponse> getAuThor() {
        try {
            return authorRepositoryCustom.getAuthorsByStatus(1);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<AuthorResponse> getAuthorRequest() {
        try {
            return authorRepositoryCustom.getAuthorsByStatus(0);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


}
