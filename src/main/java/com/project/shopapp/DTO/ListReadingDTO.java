package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListReadingDTO {
    @JsonProperty("title")
    private String title;


    @JsonProperty("coverimage")
    private String coverimage;

    @JsonProperty("namecategory")
    private Set<String> namecategory;


    @JsonProperty("authors")
    private Set<String> authorName;


    @JsonProperty("description")
    private String description;

    @JsonProperty("publishyear")
    private int publishyear;
}
