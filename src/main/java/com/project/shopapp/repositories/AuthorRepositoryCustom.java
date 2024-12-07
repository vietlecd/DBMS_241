package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.responses.AuthorResponse;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositoryCustom {

    List<AuthorResponse> getAuthorsByStatus(int status);

}
