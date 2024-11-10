package com.project.shopapp.repositories.impl;

import com.project.shopapp.repositories.CateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CateRepositoryImpl implements CateRepository {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASS;

    @Override
    public Long findCateIdByBookId(Long bookID) {
        String sql = "SELECT cateID FROM cate WHERE bookID = " + bookID;
        Long cateID = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                cateID = rs.getLong("cateID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cateID;
    }
}
