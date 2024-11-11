/*
package com.project.shopapp.repositories.impl;

import com.project.shopapp.repositories.CategoryRepositoryCustom;
import com.project.shopapp.repositories.entity.CategoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<CategoryEntity> findByParamsAndTypeCode(Map<String, Object> params, List<String> name) {
        // Khởi tạo câu truy vấn JPQL cho CategoryEntity
        StringBuilder where = new StringBuilder("SELECT DISTINCT c FROM CategoryEntity c WHERE 1=1");

        // Thêm các điều kiện khác từ params
        params.forEach((key, value) -> {
            if (value != null && !"name".equals(key)) { // Bỏ qua "name" vì đã xử lý ở dưới
                String cleanedValue = value.toString().replaceAll("[\\n\\r]+", "").trim();

                if (isNumeric(cleanedValue)) {
                    where.append(" AND c.").append(key).append(" = :").append(key);
                } else {
                    where.append(" AND c.").append(key).append(" LIKE :").append(key);
                }

                params.put(key, cleanedValue);
            }
        });

        // Nếu danh sách name không rỗng, thêm điều kiện lọc theo name
        if (name != null && !name.isEmpty()) {
            List<String> nameList = new ArrayList<>();
            for (String item : name) {
                nameList.add("'" + item + "'");
            }
            where.append(" AND c.name IN (" + String.join(",", nameList) + ")");
        }

        System.out.println("Generated JPQL Query: " + where);

        // Tạo đối tượng Query từ JPQL
        Query query = entityManager.createQuery(where.toString(), CategoryEntity.class);

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
        List<CategoryEntity> resultList = query.getResultList();

        // In số lượng kết quả để kiểm tra
        System.out.println("Query result count: " + resultList.size());

        // Trả về danh sách các CategoryEntity
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
*/
