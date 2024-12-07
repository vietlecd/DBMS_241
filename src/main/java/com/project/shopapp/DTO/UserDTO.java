package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data //toString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    //id
    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @JsonProperty("username")
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @NotNull(message = "Role id is required")
    @JsonProperty("role_id")
    private Long roleId;
}
