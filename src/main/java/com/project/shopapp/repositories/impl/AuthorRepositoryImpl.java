package com.project.shopapp.repositories.impl;

import com.project.shopapp.repositories.AuthorRepositoryCustom;
import com.project.shopapp.responses.AuthorResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    // Gọi stored procedure getAuthorsByStatus và ánh xạ vào AuthorResponse
    public List<AuthorResponse> getAuthorsByStatus(int status) {
        // Tạo một native query (gọi trực tiếp SQL query)
        String sql = "CALL getAuthorsByStatus(:status)";

        // Tạo query
        Query query = entityManager.createNativeQuery(sql);

        // Đặt tham số cho stored procedure
        query.setParameter("p_status", status);

        // Thiết lập ánh xạ vào AuthorResponse
        List<Object[]> resultList = query.getResultList();

        // Nếu có kết quả, ánh xạ thủ công
        return resultList.stream()
                .map(row -> new AuthorResponse(
                        (String) row[0],  // username
                        (String) row[1],  // fullName
                        (String) row[2],  // id_card
                        (String) row[3],  // bio
                        (String) row[4]   // phone_number
                ))
                .toList();
    }
}
