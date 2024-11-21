package com.project.shopapp.services;

import com.project.shopapp.DTO.ReportDTO;
import com.project.shopapp.models.User;
import org.springframework.http.ResponseEntity;

public interface IReportService {
    ResponseEntity<?> addReportToBook(Long bookID, ReportDTO reportDTO, User user);
    ResponseEntity<?> findReportsByBookId(Long bookID, String username);
    ResponseEntity<?> deleteReportByBookIdAndReportId(Long bookID, int reportID, User user);
}
