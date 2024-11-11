package com.project.shopapp.responses;

import lombok.*;

@Data //toString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTOResponse {
    private String username;
    private String fullName;
    private String phoneNumber;


}

