package com.project.shopapp.repositories;

import com.project.shopapp.models.BookMark;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarksRepository extends JpaRepository<BookMark, Long> {
}
