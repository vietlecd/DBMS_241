package com.project.shopapp.repositories;

import com.project.shopapp.models.ListReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListRepository extends JpaRepository<ListReading, Integer> {

}
