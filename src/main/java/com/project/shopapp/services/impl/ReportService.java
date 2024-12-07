package com.project.shopapp.services.impl;


import com.project.shopapp.DTO.ReportDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Report;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.ReportRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService implements IReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public ResponseEntity<?> addReportToBook(Long bookID, ReportDTO reportDTO, User user) {
        Optional<Book> optionalBook = bookRepository.findById(bookID);
        if (!optionalBook.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy Book với ID: " + bookID, HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy User với username: ");
        }

        Book book = optionalBook.get();
        Report report = new Report();
        report.setBook(book);

        report.setReportContent(reportDTO.getReportContent());
        report.setReportDate(LocalDateTime.now());
        report.setUser(user);
        reportRepository.save(report);

        book.getReports().add(report);
        // Tạo đối tượng DTO để trả về
        // Định dạng createDate sang ISO-8601
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        ReportDTO savedReportDTO = new ReportDTO();
        savedReportDTO.setReportContent(report.getReportContent()); // Nội dung báo cáo
        savedReportDTO.setUsername(user.getUsername()); // Thêm username vào DTO trả về
        savedReportDTO.setReportDate(
                report.getReportDate().format(isoFormatter) // Định dạng LocalDateTime thành chuỗi
        );

// Trả về đối tượng DTO
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(savedReportDTO);

    }

    @Override
    public ResponseEntity<?> findReportsByBookId(Long bookID, String username) {
        Optional<Book> optionalBook = bookRepository.findById(bookID);
        if (!optionalBook.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy Book với ID: " + bookID, HttpStatus.BAD_REQUEST);
        }

            List<ReportDTO> reportDTOs = reportRepository.findByBook_BookID(bookID)
                    .stream()
                    .map(report -> {
                        ReportDTO dto = new ReportDTO();

                        // Gán giá trị cho reportContent
                        dto.setReportContent(report.getReportContent());

                        // Định dạng reportDate
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        dto.setReportDate(report.getReportDate().format(formatter));

                        // Gán username từ User liên kết
                        dto.setUsername(report.getUser().getUsername());

                        return dto;
                    })
                    .collect(Collectors.toList());

            // Trả về danh sách reports dưới dạng ResponseEntity
            return ResponseEntity.ok(reportDTOs);


    }

    @Override
    public ResponseEntity<?> deleteReportByBookIdAndReportId(Long bookID, int reportID, User user) {
        Report report = reportRepository.findByBook_BookIDAndRpID(bookID, reportID);
        if (report == null || !report.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body("Unauthorized to delete this report");
        }
        reportRepository.delete(report);
        return ResponseEntity.ok("Report deleted successfully");
    }
}
