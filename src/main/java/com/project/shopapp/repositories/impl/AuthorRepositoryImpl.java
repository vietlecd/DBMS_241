package com.project.shopapp.repositories.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.models.Author;
import com.project.shopapp.repositories.AuthorRepositoryCustom;
import com.project.shopapp.responses.AuthorResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public List<AuthorResponse> getAuthorsByStatus(int p_status) {
        String sql = "CALL getAuthorsByStatus(:p_status)";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("p_status", p_status);

        List<Object[]> resultList = query.getResultList();

        if (resultList == null || resultList.isEmpty()) {
            return List.of();
        }

        return resultList.stream()
                .map(row -> {
                    Map<String, Object> authorMap = new HashMap<>();
                    authorMap.put("username", row[0]);
                    authorMap.put("fullname", row[1]);
                    authorMap.put("phone_number", row[2]);
                    authorMap.put("id_card", row[3]);
                    authorMap.put("bio", row[4]);

                    return objectMapper.convertValue(authorMap, AuthorResponse.class);
                })
                .collect(Collectors.toList());
    }
}
