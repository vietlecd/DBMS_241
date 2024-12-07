package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data //toString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    @JsonProperty("bio")
    @NotBlank(message = "bio is required")
    private String bio;

    @JsonProperty("id_card")
    @NotBlank(message = "ID Card is required")
    private String id_card;



}
