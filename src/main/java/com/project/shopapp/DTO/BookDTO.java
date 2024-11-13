package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.Author;
import lombok.*;

import java.util.Set;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @JsonProperty("book_id")
    private Long bookID;

    @JsonProperty("title")
    private String title;


    @JsonProperty("cover_image")
    private String coverImage;

    @JsonProperty("category_description")
    private String cateDescription;

    @JsonProperty("price")
    private String price;


    @JsonProperty("status")
    private String status;

    @JsonProperty("namecategory")
    private String namecategory;


    @JsonProperty("authors")
    private Set<String> authorName;


    @JsonProperty("description")
    private String description;

    @JsonProperty("publish_year")
    private int publishYear;









}
