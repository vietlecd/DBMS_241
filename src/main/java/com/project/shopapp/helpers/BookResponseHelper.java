package com.project.shopapp.helpers;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Book;
import com.project.shopapp.responses.BookAuthorResponse;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookResponseHelper {

    public List<BookAuthorResponse> bookListGet(List<Book> bookList) {
        if (bookList.isEmpty()) {
            throw new DataNotFoundException("Book not found");
        }

        List<BookAuthorResponse> responseList = new ArrayList<>();

        for (Book book : bookList) {
            BookAuthorResponse res = BookAuthorResponse.builder()
                    .price(book.getPrice())
                    .title(book.getTitle())
                    .coverimage(book.getCoverimage())
                    .description(book.getDescription())
                    .publishyear(book.getPublishyear())
                    .username(book.getUploader().getUsername())
                    .build();

            responseList.add(res);
        }
        return responseList;
    }
}
