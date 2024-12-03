package com.project.shopapp.repositories;


import com.project.shopapp.models.Book;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.responses.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Book findByBookID(Integer bookID);

    @Query("SELECT b FROM Book b " +
            "WHERE b.status = 'true' AND b.bookID = :bookID")
    Book findBookByBookIDAndStatus(@Param("bookID") Integer bookID);

    @Query("SELECT b.bookID AS bookId, b.title AS title, b.coverimage AS coverImage, b.description AS description, " +
            "b.publishyear AS publishYear, b.price AS price, " +
            "a.user.fullName AS authorName, c.namecategory AS categories " +
            "FROM Book b " +
            "JOIN b.categories c " +
            "JOIN b.authorList a " +
            "WHERE a.user.fullName = :authorName AND b.status = 'true'")
    List<BookProjection> findBookByAuthorName(@Param("authorName") String authorName);


    @Query("SELECT new com.project.shopapp.responses.BookAuthorResponse(b.bookID, b.title, b.description, b.coverimage, b.publishyear, b.price, a.id, u.id) " +
            "FROM Book b " +
            "JOIN b.authorList a " +
            "JOIN a.user u " +
            "WHERE u.id = :userId AND b.status='true'")
    List<BookAuthorResponse> findBooksByUserId(@Param("userId") Integer userId);

}
