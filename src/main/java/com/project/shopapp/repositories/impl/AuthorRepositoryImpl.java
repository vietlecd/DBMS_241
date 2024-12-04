package com.project.shopapp.repositories.impl;

import com.project.shopapp.models.Author;
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
                .map(row -> new AuthorResponse(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4]
                ))
                .toList();
    }
    @Override
    public Author getAuthorByUsernameAndStatus(String p_username, int p_status) {
        String sql = "CALL getAuthorByUsernameAndStatus(:p_username, :p_status)";
        Query query = entityManager.createNativeQuery(sql, Author.class);

        query.setParameter("p_username", p_username);
        query.setParameter("p_status", p_status);

        List<Author> result = query.getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
