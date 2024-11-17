package com.project.shopapp.repositories;

import com.project.shopapp.models.BookMark;

import com.project.shopapp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarksRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findByBook_BookID(Long bookID);
}
