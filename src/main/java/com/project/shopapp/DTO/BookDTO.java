package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Arrays;
import java.util.HashSet;
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
    @NotBlank(message = "title kh duoc de trong")
    private String title;

    @JsonProperty("coverimage")
    @NotBlank(message = "coverimage kh duoc de trong")
    private String coverimage;

    @JsonProperty("catedescription")
    private String catedescription;

    @JsonProperty("price")
    @NotBlank(message = "price kh duoc de trong")
    private String price;

    private User uploader;

    @JsonProperty("status")
    private String status;

    @JsonProperty("namecategory")
    @NotBlank(message = "category kh duoc de trong")
    private Set<String> namecategory;

    @JsonProperty("description")
    @NotBlank(message = "mo ta sach kh duoc de trong")
    private String description;

    @JsonProperty("publishyear")
    @NotBlank(message = "nam phat hanh kh duoc de trong")
    private int publishyear;

    @JsonProperty("totalpage")
    @NotBlank(message = "tong so trang kh duoc de trong")
    private int totalpage;

    @JsonProperty("username")
    @NotBlank(message = "username kh duoc de trong")
    private Set<String> username;

    @JsonProperty("pdf")
    private String pdf;

    public void setNamecategory(@JsonProperty("namecategory") String namecategoryString) {
        if (namecategoryString != null && !namecategoryString.isEmpty()) {
            this.namecategory = new HashSet<>(Arrays.asList(namecategoryString.split(",\\s*")));
        } else {
            this.namecategory = new HashSet<>();
        }
    }





}
