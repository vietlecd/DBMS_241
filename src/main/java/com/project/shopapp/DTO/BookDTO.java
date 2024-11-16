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
    @JsonProperty("bookID")
    private Integer bookID;

    @JsonProperty("title")
    private String title;


    @JsonProperty("coverimage")
    private String coverimage;

    @JsonProperty("catedescription")
    private String catedescription;

    @JsonProperty("price")
    private String price;


    @JsonProperty("status")
    private String status;

    @JsonProperty("namecategory")
    private Set<String> namecategory;


    @JsonProperty("authors")
    private Set<String> authorName;


    @JsonProperty("description")
    private String description;

    @JsonProperty("publishyear")
    private int publishyear;









}
