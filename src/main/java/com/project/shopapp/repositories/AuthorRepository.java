package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.AuthorResponse;
import com.project.shopapp.responses.BaseProjection;
import com.project.shopapp.responses.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
    Author findAuthorByUser(User user);
    boolean existsAuthorByUser(User user);
    Author findAuthorByUser_Username(String username);

}
