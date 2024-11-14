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
public class ReviewDTO {


    @JsonProperty("rating")
    private Long rating;
    @JsonProperty("comments")
    private Set<String> comments;
    @JsonProperty("reviewname")
    private String reviewname;



}
