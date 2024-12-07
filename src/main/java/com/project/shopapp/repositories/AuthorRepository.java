package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>{
    boolean existsAuthorByUser(User user);
    Optional<Author> findAuthorByUser_Username(String username);

    List<Author> findAuthorByUser_FullName(String fullName);

    @Procedure(procedureName = "count_followers")
    Integer count_followers(@Param("p_author_id") Integer p_author_id);

    @Procedure(procedureName = "count_recommended_books_by_author")
    Integer count_recommended_books_by_author(@Param("p_author_id") Integer p_author_id);

}
