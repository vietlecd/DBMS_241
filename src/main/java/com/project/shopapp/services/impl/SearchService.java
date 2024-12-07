package com.project.shopapp.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopapp.helpers.BookResponseHelper;
import com.project.shopapp.models.Book;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.services.ISearchService;
import com.project.shopapp.utils.CheckExistedUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class SearchService implements ISearchService {
    private RedisTemplate<String, String> redisTemplate;
    private ObjectMapper objectMapper;
    private BookResponseHelper bookResponseHelper;
    private BookRepository bookRepository;
    private CheckExistedUtils checkExistedUtils;

    @Override
    public Set<String> getAutocompleteSuggestions(String keyword) {
        String start = keyword;
        String end = keyword + (char) 127;

        Set<String> suggestions = redisTemplate.opsForZSet()
                .rangeByLex("autocomplete::titles", Range.closed(start, end));

        if (suggestions == null || suggestions.isEmpty()) {
            return Collections.emptySet();
        }

        return suggestions;
    }


    @Override
    public List<BookAuthorResponse> searchBooks(String keyword, int page, int pageSize) {
        String cacheKey = "search::" + keyword + "::page_" + page;

        String cachedResult = redisTemplate.opsForValue().get(cacheKey);
        if (cachedResult != null) {
            try {
                List<BookAuthorResponse> bookList = objectMapper.readValue(cachedResult, new TypeReference<List<BookAuthorResponse>>() {});
                return bookList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Book> books = bookRepository.search_books(keyword, page, pageSize);
        checkExistedUtils.checkObjectExisted(books, "books");
        List<BookAuthorResponse> responseList = bookResponseHelper.bookListGet(books);

        for (Book book : books) {
            String title = book.getTitle();
            if (title != null && !title.isEmpty()) {
                Boolean isMember = redisTemplate.opsForZSet().score("autocomplete::titles", title) != null;
                if (!isMember) {
                    redisTemplate.opsForZSet().add("autocomplete::titles", title, 0);
                }
            }
        }

        try {
            String bookJson = objectMapper.writeValueAsString(responseList);
            checkExistedUtils.checkObjectExisted(bookJson, "bookJson");
            redisTemplate.opsForValue().set(cacheKey, bookJson);
        } catch (Exception e) {
            throw new RuntimeException("Error while caching books: " + e.getMessage());
        }
        redisTemplate.expire(cacheKey, Duration.ofHours(3)); //luu dc toi da 3 hours thoi...

        return responseList;
    }
}
