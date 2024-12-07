package com.project.shopapp.responses;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class BookAuthorResponse {
    private String title;
    private String description;
    private String coverimage;
    private Integer publishyear;
    private String price;
    private String username;
    private String pdf;
    private Set<String> categoryNames;
    private Set<String> authorName;
}
