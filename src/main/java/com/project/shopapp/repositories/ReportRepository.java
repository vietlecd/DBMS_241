package com.project.shopapp.repositories;

import com.project.shopapp.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByBook_BookID(Long bookID);
    Report findByBook_BookIDAndRpID(Long bookID, int rpID);

}
