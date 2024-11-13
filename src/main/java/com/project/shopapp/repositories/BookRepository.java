package com.project.shopapp.repositories;



import com.project.shopapp.models.Book;

import com.project.shopapp.DTO.BookDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.responses.BookProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Book findByTitle(String title);


    List<Book> findByStatus(String status);
    Book findByBookID(Long bookID);

    @Query("SELECT b.bookID AS bookId, b.title AS title, b.coverimage AS coverImage, b.description AS description, " +
            "b.publishyear AS publishYear, b.price AS price, a.userId.fullName AS authorName, " +
            "GROUP_CONCAT(c.catedescription) AS categories " +
            "FROM Book b " +
            "JOIN b.categories c " +
            "JOIN Author a " +
            "WHERE a.userId.fullName = :authorName " +
            "GROUP BY b.bookID, b.title, b.coverimage, b.description, b.publishyear, b.price, a.userId.fullName")
    List<BookProjection> findBookByAuthorName(@Param("authorName") String authorName);

}
