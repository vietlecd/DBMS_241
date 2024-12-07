package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.ListReadingDTO;
import com.project.shopapp.helpers.BookResponseHelper;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.ListReading;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.ListReadingRepository;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.services.IListReadingService;
import com.project.shopapp.utils.CheckExistedUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListReadingService implements IListReadingService {
    private BookRepository bookRepository;
    private ListReadingRepository listReadingRepository;
    private CheckExistedUtils checkExistedUtils;
    private BookResponseHelper bookResponseHelper;

    @Override
    public ResponseEntity<?> addList(User user, Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        checkExistedUtils.checkObjectExisted(book, "Book");

        Optional<ListReading> exList = listReadingRepository.findFirstByUser(user);
        ListReading listReading1 = exList.orElseGet(() -> {
            ListReading newList = ListReading.builder()
                    .user(user)
                    .bookSet(new HashSet<>())
                    .build();
            return listReadingRepository.save(newList);
        });

        if(listReading1.getBookSet().contains(book)) {
            return ResponseEntity.badRequest().body("This book already in your list");
        }

        listReading1.addBook(book);

        listReadingRepository.save(listReading1);

        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> deList(User user, Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        checkExistedUtils.checkObjectExisted(book, "Book");

        Optional<ListReading> listReadingOptional = listReadingRepository.findFirstByUser(user);
        checkExistedUtils.checkObjectExisted(listReadingOptional, "ListReading");

        ListReading listReading = listReadingOptional.get();
        boolean removed = listReading.removeBook(book);
        checkExistedUtils.checkStatusExisted(removed, "ListReading");

        listReadingRepository.save(listReading);

        return ResponseEntity.ok("");

    }

    @Override
    public ResponseEntity<?> getList(User user) {
        Optional<ListReading> listReadingOptional = listReadingRepository.findFirstByUser(user);
        checkExistedUtils.checkObjectExisted(listReadingOptional, "ListReading");

        ListReading listReading = listReadingOptional.get();

        List<BookAuthorResponse> res = bookResponseHelper.bookListGet(listReading.getBookSet().stream().toList());

        return ResponseEntity.ok(res);
    }

}
