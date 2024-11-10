package com.project.shopapp.repositories;

import com.project.shopapp.repositories.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository {
    CategoryEntity findById(Long id);
}
