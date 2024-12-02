package com.project.shopapp.repositories.impl;

import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.BookRepositoryCustom;
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
    public List<Book> findByParamsAndTypeCode(Map<String, Object> params) {
        // Khởi tạo chuỗi JPQL cho truy vấn
        StringBuilder jpql = new StringBuilder("SELECT DISTINCT b FROM Book b");

        // Kiểm tra nếu tham số "name" có mặt thì thêm JOIN với Category
        if (params.containsKey("namecategory")) {
            jpql.append(" JOIN b.categories c"); // Thêm JOIN với Category nếu có name
        }

        jpql.append(" WHERE 1=1");

        // Thêm các điều kiện từ params
        params.forEach((key, value) -> {
            if (value != null) {
                String cleanedValue = value.toString().replaceAll("[\\n\\r]+", "").trim();
                if ("namecategory".equals(key)) {
                    // Điều kiện đặc biệt cho tham số "name" (tìm theo tên của category)
                    jpql.append(" AND c.namecategory LIKE :namecategory");
                    params.put("namecategory", cleanedValue);
                } else if (isNumeric(cleanedValue)) {
                    jpql.append(" AND b.").append(key).append(" = :").append(key);
                    params.put(key, cleanedValue);
                } else {
                    jpql.append(" AND b.").append(key).append(" LIKE :").append(key);
                    params.put(key, cleanedValue);
                }
            }
        });

        System.out.println("Generated JPQL Query: " + jpql);

        // Tạo đối tượng Query từ JPQL
        Query query = entityManager.createQuery(jpql.toString(), Book.class);

        // Gán giá trị cho các tham số trong truy vấn
        params.forEach((key, value) -> {
            if (value != null) {
                if (isNumeric(value.toString())) {
                    query.setParameter(key, value);
                } else {
                    query.setParameter(key, "%" + value + "%");
                }
            }
        });

        // Thực thi truy vấn và lấy kết quả
        List<Book> resultList = query.getResultList();

        // In số lượng kết quả để kiểm tra
        System.out.println("Query result count: " + resultList.size());

        // In chi tiết từng đối tượng trả về để kiểm tra dữ liệu
        for (Book book : resultList) {
            System.out.println("Book ID: " + book.getBookID() + ", Title: " + book.getTitle());


            // In ra các thuộc tính của CategoryEntity liên kết với BookEntity
            for (Category category : book.getCategories()) {
                System.out.println("Category Name: " + category.getNamecategory() + ", Description: " + category.getCatedescription());

            }
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
