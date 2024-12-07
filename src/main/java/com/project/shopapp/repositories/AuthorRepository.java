package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{
    boolean existsAuthorByUser(User user);
    Optional<Author> findAuthorByUser_Username(String username);

    List<Author> findAuthorByUser_FullName(String fullName);

}
