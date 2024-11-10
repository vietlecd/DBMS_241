package com.project.shopapp.repositories;

import com.project.shopapp.repositories.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, BookRepositoryCustom {
}
