package com.project.shopapp.services;

import com.project.shopapp.responses.BookAuthorResponse;

import java.util.List;
import java.util.Set;

public interface ISearchService {
    Set<String> getAutocompleteSuggestions(String keyword);
    List<BookAuthorResponse> searchBooks(String keyword, int page, int pageSize);
}
