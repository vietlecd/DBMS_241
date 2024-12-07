package com.project.shopapp.helpers;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Author;
import com.project.shopapp.repositories.AuthorRepository;
import com.project.shopapp.repositories.AuthorRepositoryCustom;
import com.project.shopapp.responses.AuthorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorHelper {
    @Autowired
    private AuthorRepositoryCustom authorRepositoryCustom;
    @Autowired
    private AuthorRepository authorRepository;

    public Optional<Author> getAuthorByUsernameAndStatus(String authorUsername, int p_status) {
        List<AuthorResponse> authors = authorRepositoryCustom.getAuthorsByStatus(p_status);

        if (authors.isEmpty()) {
            throw new DataNotFoundException("Author not found");
        }

        Optional<AuthorResponse> authorResponseOpt = authors.stream()
                .filter(author -> author.getUsername().equals(authorUsername))
                .findFirst();

        if (authorResponseOpt.isEmpty()) {
            throw new DataNotFoundException("Author not found: " + authorUsername);
        }

        String authorUser = authorResponseOpt.get().getUsername();
        Optional<Author> author = authorRepository.findAuthorByUser_Username(authorUsername);

        if (author.isEmpty()) {
            throw new DataNotFoundException("Author not found in the database with ID: " + authorUser);
        }

        return author;
    }

}
