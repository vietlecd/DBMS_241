package com.project.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {

    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("id_card")
    private String id_card;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("phone_number")
    private String phone_number;

    @JsonProperty("username")
    private String username;
}
