package com.project.shopapp.repositories;



import com.project.shopapp.models.BookEntity;

import java.util.List;
import java.util.Map;

public interface BookRepositoryCustom {
    List<BookEntity> findByParamsAndTypeCode(Map<String, Object> params);
}
