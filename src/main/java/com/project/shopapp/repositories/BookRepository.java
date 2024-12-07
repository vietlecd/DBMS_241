package com.project.shopapp.repositories;


import com.project.shopapp.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByBookID(Integer bookID);

    @Procedure(procedureName = "count_book_view")
    Integer count_book_view(@Param("bookId") Integer bookId);

    @Procedure(procedureName = "find_book_bought")
    List<Book> findBookBought(@Param("p_username") String username);

    @Procedure(procedureName = "find_book_by_author")
    List<Book> findBooksByAuthorUsername(@Param("p_author_username") String p_author_username);

    @Procedure(procedureName = "count_book_written")
    Integer count_book_written(@Param("p_author_username") String p_author_username);

    @Procedure(procedureName = "GetBooksByParams")
    List<Book> GetBooksByParams(@Param("categoryName") String categoryName);

    @Procedure(procedureName = "deleteAuthorBookByBookID")
    void deleteAuthorBookByBookID(@Param("bookID") Integer bookID);

    @Procedure(procedureName = "search_books")
    List<Book> search_books(@Param("keyword") String keyword, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    @Procedure(procedureName = "get_free_books")
    List<Book> get_free_books();

    @Procedure(procedureName = "get_recommend_books")
    List<Book> get_recommend_books();
}
