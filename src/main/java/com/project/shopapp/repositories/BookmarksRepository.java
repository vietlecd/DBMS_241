package com.project.shopapp.repositories;

import com.project.shopapp.models.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarksRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findByBook_BookID(Long bookID);
    Optional<BookMark> findByBook_BookIDAndPageNumberAndUser_Id(Long bookID, Integer pageNumber, Integer userId);
}
