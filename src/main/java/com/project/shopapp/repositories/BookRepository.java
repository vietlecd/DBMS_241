package com.project.shopapp.repositories;


import com.project.shopapp.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    Book findByTitle(String title);

    List<Book> findByStatus(String status);
    Book findByBookID(Long bookID);
}
