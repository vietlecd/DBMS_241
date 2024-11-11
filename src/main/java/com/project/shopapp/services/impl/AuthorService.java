package com.project.shopapp.services.impl;

import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.AuthorRespository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorAService implements IAuthorService {
    private UserRepository userRepository;
    private AuthorRespository authorRespository;
    public ResponseEntity<String> becomeAuthor(String myUsername) {
        User user = userRepository.findByUsername(myUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + myUsername));

        if (!authorRespository.existsAuthorById(user.getId())) {
            Author author = new Author();
            author.set
        }




        return null;
    }
}
