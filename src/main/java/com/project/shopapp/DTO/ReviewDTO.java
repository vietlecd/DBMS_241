package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    private String comment;
    @JsonProperty("username")
    private String username;
    @JsonProperty("evaluate")
    private String evaluate;



}
