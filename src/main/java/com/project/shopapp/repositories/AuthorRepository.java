package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.BaseProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Boolean existsAuthorByUserId(User userId);

    Boolean existsAuthorByIdCard(String idCard);

    @Query("SELECT a FROM Author a WHERE a.userId.id = :userId AND a.status = 1")
    Optional<Author> findByUserIdAndStatus(@Param("userId") Integer userId);

    @Query("SELECT a FROM Author a WHERE a.userId.id = :userId")
    Optional<Author> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT a FROM Author a WHERE a.userId.username = :username")
    Optional<Author> findAuthorByFullname(@Param("username") String username);

    @Query("SELECT a.userId.username as username, a.userId.fullName as fullName, a.userId.phoneNumber as phoneNumber " +
            "FROM Author a " +
            "WHERE a.status = 1")
    List<BaseProjection> findAllAuthors();

    @Query("SELECT a.userId.username as username, a.userId.fullName as fullName, a.userId.phoneNumber as phoneNumber " +
            "FROM Author a " +
            "WHERE a.status = 0")
    List<BaseProjection> findAllAuthorRequests();
}
