package com.project.shopapp.responses.Impl;

import com.project.shopapp.responses.BookProjection;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BookProjectionImpl implements BookProjection {
    private Integer bookID;
    private String title;
    private String coverImage;
    private String description;
    private int publishYear;
    private String price;
    private String authorName;
    private List<String> categories;

    public BookProjectionImpl(BookProjection projection) {
        this.bookID = projection.getBookID();
        this.title = projection.getTitle();
        this.coverImage = projection.getCoverImage();
        this.description = projection.getDescription();
        this.publishYear = projection.getPublishYear();
        this.price = projection.getPrice();
        this.authorName = projection.getAuthorName();
        this.categories = new ArrayList<>();
    }
}
