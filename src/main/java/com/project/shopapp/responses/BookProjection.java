package com.project.shopapp.responses;

import java.util.List;

public interface BookProjection {
    Long getBookID();
    String getTitle();
    String getCoverImage();
    String getDescription();
    int getPublishYear();
    String getPrice();

    String getAuthorName();
    List<String> getCategories();
}
