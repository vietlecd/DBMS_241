package com.project.shopapp.repositories;



import com.project.shopapp.models.Book;

import java.util.List;
import java.util.Map;

public interface BookRepositoryCustom {
    List<Book> findByParamsAndTypeCode(Map<String, Object> params);
}
