package com.project.shopapp.repositories;

import com.project.shopapp.models.Author;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.AuthorDTOResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Boolean existsAuthorByUserId(User userId);

//    @Query("SELECT a FROM Author a WHERE a.userId.id = :userId AND a.status=1")
//    Optional<Author> findByUserId(@Param("userID") Integer userId);

    @Query("SELECT a FROM Author a WHERE a.userId.id = :userId AND a.status = 1")
    Optional<Author> findByUserIdAndStatus(@Param("userId") Integer userId);

    @Query("SELECT a FROM Author a WHERE a.userId.id = :userId")
    Optional<Author> findByUserId(@Param("userId") Integer userId);

    @Query("SELECT new com.project.shopapp.responses.AuthorDTOResponse(a.userId.username, a.userId.fullName, a.userId.phoneNumber) " +
            "FROM Author a")
    List<AuthorDTOResponse> findAllAuthors();

    @Query("SELECT new com.project.shopapp.responses.AuthorDTOResponse(a.userId.username, a.userId.fullName, a.userId.phoneNumber) " +
            "FROM Author a " +
            "WHERE a.status = 0")
    List<AuthorDTOResponse> findAllAuthorsWithStatus();
}
