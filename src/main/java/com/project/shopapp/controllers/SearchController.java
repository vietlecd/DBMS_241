package com.project.shopapp.controllers;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

@Controller
@RequestMapping("${api.prefix}/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @GetMapping("/autoComplete")
    public ResponseEntity<?> getAutoComplete(@RequestParam String keyword) {
        try {
            Set<String> res = searchService.getAutocompleteSuggestions(keyword);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> searchBooks(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        try {
            List<BookAuthorResponse> res = searchService.searchBooks(keyword, page, pageSize);
            return ResponseEntity.ok(res);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
