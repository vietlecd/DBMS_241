package com.project.shopapp.repositories.impl;

import com.project.shopapp.repositories.BookRepositoryCustom;
import com.project.shopapp.repositories.entity.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<BookEntity> findByParamsAndTypeCode(Map<String, Object> params, List<String> typeCode) {
        // Khởi tạo chuỗi JPQL cho truy vấn
        StringBuilder jpql = new StringBuilder("SELECT b FROM BookEntity b WHERE 1=1");

        // Thêm điều kiện từ params vào JPQL
        params.forEach((key, value) -> {
            if (value != null) {
                // Xử lý giá trị của chuỗi để loại bỏ ký tự xuống dòng và khoảng trắng không mong muốn
                String cleanedValue = value.toString().replaceAll("[\\n\\r]+", "").trim();

                if (isNumeric(cleanedValue)) {
                    jpql.append(" AND b.").append(key).append(" = :").append(key);
                } else {
                    jpql.append(" AND b.").append(key).append(" LIKE :").append(key);
                }

                // Ghi đè giá trị đã làm sạch vào params
                params.put(key, cleanedValue);
            }
        });

        // Thêm điều kiện typeCode nếu có
        if (typeCode != null && !typeCode.isEmpty()) {
            jpql.append(" AND b.typeCode IN :typeCode");
        }

        // In chuỗi JPQL đã tạo để kiểm tra
        System.out.println("Generated JPQL Query: " + jpql);

        // Tạo đối tượng Query từ JPQL
        Query query = entityManager.createQuery(jpql.toString(), BookEntity.class);

        // Gán giá trị cho các tham số trong truy vấn
        params.forEach((key, value) -> {
            if (value != null) {
                if (isNumeric(value.toString())) {
                    query.setParameter(key, value);
                } else {
                    query.setParameter(key, "%" + value + "%"); // Thêm dấu % cho LIKE
                }
            }
        });

        if (typeCode != null && !typeCode.isEmpty()) {
            query.setParameter("typeCode", typeCode);
        }

        // Thực thi truy vấn và lấy kết quả
        List<BookEntity> resultList = query.getResultList();

        // In số lượng kết quả để kiểm tra
        System.out.println("Query result count: " + resultList.size());

        // In chi tiết từng đối tượng trả về để kiểm tra dữ liệu
        for (BookEntity book : resultList) {
            System.out.println("Book ID: " + book.getBookID() + ", Title: " + book.getTitle());
        }

        return resultList;
    }

    // Phương thức kiểm tra nếu chuỗi là số
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
