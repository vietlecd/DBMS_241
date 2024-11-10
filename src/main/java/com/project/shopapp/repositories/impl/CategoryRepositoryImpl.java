package com.project.shopapp.repositories.impl;


import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASS;

    @Override
    public CategoryEntity findById(Long id) {
        String sql = "SELECT c.name, c.cateDescription FROM category c WHERE c.cateID = " + id + ";";
        CategoryEntity categoryEntity = new CategoryEntity();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                categoryEntity.setName(rs.getString("name"));
                categoryEntity.setCateDescription(rs.getString("cateDescription"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connected database failed...");
        }
        return categoryEntity;
    }
}