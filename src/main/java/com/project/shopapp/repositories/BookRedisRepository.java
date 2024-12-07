package com.project.shopapp.repositories;

import com.project.shopapp.models.Book;
import com.project.shopapp.models.BookRedis;
import org.springframework.data.repository.CrudRepository;

public interface BookRedisRepository extends CrudRepository<BookRedis, Integer> {
}
