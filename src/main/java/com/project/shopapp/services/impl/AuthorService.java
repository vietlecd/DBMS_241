package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.AuthorDTOResponse;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService implements IAuthorService {
    private UserRepository userRepository;
    private AuthorRepository authorRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    public ResponseEntity<String> becomeAuthor(String myUsername, AuthorDTO authorDTO) {
        User user;

        try {
            user = userRepository.findByUsername(myUsername)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        try {
            if (!authorRepository.existsAuthorByUserId(user)) {
                Author author = Author.builder()
                        .userId(user)
                        .bio(authorDTO.getBio())
                        .idCard(authorDTO.getId_card())
                        .status(0)
                        .build();

                if (!authorRepository.existsAuthorByIdCard(authorDTO.getId_card())) {
                    authorRepository.save(author);
                } else {
                    return new ResponseEntity<>("IDCard (CCCD) Existed", HttpStatus.FORBIDDEN);
                }
            }
            else
                return new ResponseEntity<>("Author Existed", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Them request author khong success: " + e.getMessage(), HttpStatus.FORBIDDEN);
        }


        return new ResponseEntity<>("Thêm request thành cong", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> acceptedAuthor(String username) {
        User user;

        try {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + username));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        Optional<Author> author = authorRepository.findByUserId(user.getId());
        if (author.isPresent() && author.get().getStatus() == 0) {
            Author author1 = author.get();
            author1.setStatus(1);
            authorRepository.save(author1);

            Role authorRole = new Role();
            authorRole.setId(0);

            User user1 = author1.getUserId();
            user1.setRole(authorRole);

            userRepository.save(user1);

        } else {
            return new ResponseEntity<>("Khong tim thay Author" + username, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Them thanh cong author", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deniedAuthor(String username) {
        User user;

        try {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + username));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        Optional<Author> author = authorRepository.findByUserId(user.getId());
        if (author.isPresent() && author.get().getStatus() == 0) {
            Author author1 = author.get();
            author1.setStatus(-1);
            authorRepository.save(author1);
        } else {
            return new ResponseEntity<>("Khong tim thay Author" + username, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Them tu choi Author thanh cong", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deleteAuthor(String username) {
        User user;

        try {
            user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DataNotFoundException("User not found: " + username));
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        Optional<Author> author = authorRepository.findByUserIdAndStatus(user.getId());
        if (author.isPresent() && author.get().getStatus() == 1) {
            Author author1 = author.get();
            Role authorRole = new Role();
            authorRole.setId(2);

            User user1 = author1.getUserId();
            user1.setRole(authorRole);

            userRepository.save(user1);

            authorRepository.delete(author.get());
            return new ResponseEntity<>("Delete author successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No author found", HttpStatus.FORBIDDEN);
    }

    public List<AuthorDTOResponse> getAuThor() {
        try {
            return authorRepository.findAllAuthors();
        } catch (Exception e) {
            //logger.error("An error occurred while fetching authors", e);
            return Collections.emptyList();
        }
    }

    public List<AuthorDTOResponse> getAuthorRequest() {
        try {
            return authorRepository.findAllAuthorsWithStatus();
        } catch (Exception e) {
            //logger.error("An error occurred while fetching authors", e);
            return Collections.emptyList();
        }
    }
}
