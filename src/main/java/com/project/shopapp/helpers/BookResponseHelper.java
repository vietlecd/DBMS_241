package com.project.shopapp.helpers;

import com.project.shopapp.customexceptions.DataNotFoundException;
import com.project.shopapp.models.Author;
import com.project.shopapp.models.Book;
import com.project.shopapp.models.Category;
import com.project.shopapp.responses.BookAuthorResponse;
import com.project.shopapp.utils.CheckExistedUtils;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookResponseHelper {
    @Autowired
    private CheckExistedUtils checkExistedUtils;

    public List<BookAuthorResponse> bookListGet(List<Book> bookList) {
        if (bookList.isEmpty()) {
            throw new DataNotFoundException("Book not found");
        }

        List<BookAuthorResponse> responseList = new ArrayList<>();

        for (Book book : bookList) {

            Set<String> authorSet = book.getAuthorList().stream()
                    .map(author -> author.getUser().getFullName())
                    .collect(Collectors.toSet());

            checkExistedUtils.checkObjectExisted(authorSet, "AuthorSet");

            Set<String> nameCategories = book.getCategories().stream()
                    .map(Category::getNamecategory)
                    .collect(Collectors.toSet());

            checkExistedUtils.checkObjectExisted(nameCategories, "categories");

            BookAuthorResponse res = BookAuthorResponse.builder()
                    .price(book.getPrice())
                    .title(book.getTitle())
                    .coverimage(book.getCoverimage())
                    .description(book.getDescription())
                    .publishyear(book.getPublishyear())
                    .pdf(book.getPdf())
                    .categoryNames(nameCategories)
                    .username(book.getUploader().getUsername())
                    .authorName(authorSet)
                    .build();

            responseList.add(res);
        }
        return responseList;
    }
}
