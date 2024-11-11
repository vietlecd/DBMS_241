package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.AuthorDTO;
import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRespository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService implements IAuthorService {
    private UserRepository userRepository;
    private AuthorRespository authorRespository;
    public ResponseEntity<String> becomeAuthor(String myUsername, AuthorDTO authorDTO) {
        User user = userRepository.findByUsername(myUsername)
                .orElseThrow(() -> new DataNotFoundException("User not found: " + myUsername));
        try {
            if (!authorRespository.existsAuthorByUserId(user)) {
                Author author = Author.builder()
                        .userId(user)
                        .bio(authorDTO.getBio())
                        .id_card(authorDTO.getId_card())
                        .status(0)
                        .build();

                authorRespository.save(author);
            }
            else
                return new ResponseEntity<>("Author Existed", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Them request author khong success: " + e.getMessage(), HttpStatus.FORBIDDEN);
        }


        return new ResponseEntity<>("Thêm request thành cong", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> acceptedAuthor(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found: " + username));

        if (authorRespository.existsAuthorByUserId(user)) {
            Author author = Author.builder()
                    .status(1)
                    .build();

            authorRespository.save(author);
        } else {
            return new ResponseEntity<>("Khong tim thay Author" + username, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Them thanh cong author", HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deniedAuthor(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found: " + username));

        if (authorRespository.existsAuthorByUserId(user)) {
            Author author = Author.builder()
                    .status(-1)
                    .build();
         //set tư choi

            authorRespository.save(author);
        } else {
            return new ResponseEntity<>("Khong tim thay Author" + username, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Them tu choi Author thanh cong", HttpStatus.ACCEPTED);
    }
}
