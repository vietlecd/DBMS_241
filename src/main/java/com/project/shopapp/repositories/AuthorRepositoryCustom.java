package com.project.shopapp.repositories;

import com.project.shopapp.responses.AuthorResponse;

import java.util.List;

public interface AuthorRepositoryCustom {

    // Gọi stored procedure getAuthorsByStatus và ánh xạ vào AuthorResponse
    List<AuthorResponse> getAuthorsByStatus(int status);
}
