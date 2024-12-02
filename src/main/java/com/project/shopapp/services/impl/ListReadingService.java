package com.project.shopapp.services.impl;

import com.project.shopapp.DTO.ListReadingDTO;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.ListReading;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.BookRepository;
import com.project.shopapp.repositories.ListReadingRepository;
import com.project.shopapp.services.IListReadingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ListReadingService implements IListReadingService {
    private BookRepository bookRepository;
    private ListReadingRepository listReadingRepository;

    @Override
    public ResponseEntity<?> addList(User user, Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find that book");
        }

        Set<ListReading> listReading = listReadingRepository.findAllByUserIdAndBookId(user.getId(), bookId);

        if (!listReading.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This book is already in your list");
        }

        Optional<ListReading> exList = listReadingRepository.findFirstByUserId(user);
        ListReading listReading1 = exList.orElseGet(() -> {
            ListReading newList = ListReading.builder()
                    .userId(user)
                    .bookSet(new HashSet<>())
                    .build();
            return listReadingRepository.save(newList);
        });

        listReading1.getBookSet().add(book);

        listReadingRepository.save(listReading1);

        return ResponseEntity.ok("Add successfully book into List");
    }

    @Override
    public ResponseEntity<?> deList(User user, Integer bookId) {
        Book book = bookRepository.findByBookID(bookId);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find that book");
        }

        Optional<ListReading> listReadingOptional = listReadingRepository.findFirstByUserId(user);
        if (listReadingOptional.isEmpty()) {
            return ResponseEntity.ofNullable("No list found by User");
        }

        ListReading listReading = listReadingOptional.get();
        boolean removed = listReading.getBookSet().remove(book);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found in your list");
        }

        listReadingRepository.save(listReading);

        return ResponseEntity.ok("Delete book out your list successfully");

    }

    @Override
    public ResponseEntity<?> getList(User user) {
        Optional<ListReading> listReadingOptional = listReadingRepository.findFirstByUserIdAndBooks(user.getId());
        if (listReadingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No list found by User");
        }

        ListReading listReading = listReadingOptional.get();

        Set<ListReadingDTO> bookDTOs = listReading.getBookSet().stream().map(book -> ListReadingDTO.builder()
                        .title(book.getTitle())
                        .coverimage(book.getCoverimage())
                        .namecategory(book.getCategories().stream()
                                .map(Category::getNamecategory)
                                .collect(Collectors.toSet()))
                        .authorName(book.getAuthorList().stream()
                                .map(author -> author.getUserId().getFullName())
                                .collect(Collectors.toSet()))
                        .description(book.getDescription())
                        .publishyear(book.getPublishyear())
                        .build())
                .collect(Collectors.toSet());
        return ResponseEntity.ok(bookDTOs);
    }

}
