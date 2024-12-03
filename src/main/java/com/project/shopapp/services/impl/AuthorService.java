package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.models.Author;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.customexceptions.InvalidParamException;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.AuthorRepositoryCustom;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.AuthorResponse;
import com.project.shopapp.responses.BaseProjection;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


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
//
//    @Override
//    public ResponseEntity<String> acceptedAuthor(String username) {
//        Author author = authorRepository.findAuthorByUsername(username);
//
//        try {
//            user = userRepository.findByUsername(username)
//                    .orElseThrow(() -> new DataNotFoundException("Author not found: " + username));
//        } catch (DataNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//
//        Optional<Author> author = authorRepository.findByUserId(user.getId());
//        if (author.isPresent() && author.get().getStatus() == 0) {
//            Author author1 = author.get();
//            author1.setStatus(1);
//            authorRepository.save(author1);
//
//            Role authorRole = new Role();
//            authorRole.setId(0);
//
//            User user1 = author1.getUserId();
//            user1.setRole(authorRole);
//
//            userRepository.save(user1);
//
//        } else {
//            return new ResponseEntity<>("Khong tim thay Author" + username, HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>("Them thanh cong author", HttpStatus.ACCEPTED);
//    }
//
//    @Override
//    public ResponseEntity<String> deniedAuthor(String username) {
//        User user;
//
//        try {
//            user = userRepository.findByUsername(username)
//                    .orElseThrow(() -> new DataNotFoundException("Author not found: " + username));
//        } catch (DataNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//
//        Optional<Author> author = authorRepository.findByUserId(user.getId());
//        if (author.isPresent() && author.get().getStatus() == 0) {
//            Author author1 = author.get();
//            author1.setStatus(-1);
//            authorRepository.save(author1);
//        } else {
//            return new ResponseEntity<>("Khong tim thay Author" + username, HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>("Them tu choi Author thanh cong", HttpStatus.ACCEPTED);
//    }
//
//    @Override
//    public ResponseEntity<String> deleteAuthor(String username) {
//        User user;
//
//        try {
//            user = userRepository.findByUsername(username)
//                    .orElseThrow(() -> new DataNotFoundException("Author not found: " + username));
//        } catch (DataNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//
//        Optional<Author> author = authorRepository.findByUserIdAndStatus(user.getId());
//        if (author.isPresent() && author.get().getStatus() == 1) {
//            Author author1 = author.get();
//            Role authorRole = new Role();
//            authorRole.setId(2);
//
//            User user1 = author1.getUserId();
//            user1.setRole(authorRole);
//
//            userRepository.save(user1);
//
//            authorRepository.delete(author1);
//            return new ResponseEntity<>("Delete author successfully", HttpStatus.ACCEPTED);
//        }
//        return new ResponseEntity<>("No author found", HttpStatus.FORBIDDEN);
//    }

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
