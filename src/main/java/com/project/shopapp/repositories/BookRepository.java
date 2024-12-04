package com.project.shopapp.repositories;


import com.project.shopapp.models.Book;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.responses.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Book findByBookID(Integer bookID);
    @Query(value = "CALL get_books_by_uploader_username(:p_uploader_username)", nativeQuery = true)
    List<Book> findBooksByUsername(@Param("p_uploader_username") String username);
    @Query(value = "CALL find_book_bought(:p_username)", nativeQuery = true)
    List<Book> findBookBought(@Param("p_username") String username);

}
