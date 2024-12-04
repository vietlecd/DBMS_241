package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.responses.AuthorResponse;

import java.util.List;

public interface AuthorRepositoryCustom {

    List<AuthorResponse> getAuthorsByStatus(int status);
    Author getAuthorByUsernameAndStatus(String username, int status);

}
