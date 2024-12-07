package com.project.shopapp.controllers;

import com.project.shopapp.DTO.ReportDTO;
import com.project.shopapp.helpers.AuthenticationHelper;
import com.project.shopapp.models.User;
import com.project.shopapp.services.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/report")
public class ReportController {

    @Autowired
    IReportService reportService;

    @Autowired
    private AuthenticationHelper authenticationHelper;

    // Endpoint to add a report for a book
    @PostMapping("/add/{bookID}")
    public ResponseEntity<?> addReportToBook(
            @PathVariable Long bookID,
            @RequestBody ReportDTO reportDTO,
            Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return reportService.addReportToBook(bookID, reportDTO, user);
    }

    // Endpoint to find all reports by book ID
    @GetMapping("/get/{bookID}")
    public ResponseEntity<?> getReportsByBookId(@PathVariable Long bookID, Authentication authentication) {
        String username = authenticationHelper.getUsername(authentication);
        return reportService.findReportsByBookId(bookID, username);
    }

    // Endpoint to delete a report by book ID and report ID
    @DeleteMapping("/delete/{bookID}/{reportID}")
    public ResponseEntity<?> deleteReportById(
            @PathVariable Long bookID,
            @PathVariable int reportID,
            Authentication authentication) {
        User user = authenticationHelper.getUser(authentication);
        return reportService.deleteReportByBookIdAndReportId(bookID, reportID, user);
    }
}
