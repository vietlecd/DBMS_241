package com.project.shopapp.DTO;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDTO {
   /* @JsonProperty("markID")
    private Long markID; // ID của bookmark*/

    @JsonProperty("pageNumber")
    private Long pageNumber; // Số trang được đánh dấu
    @JsonProperty("createDate")
    private String createDate; // Thời gian tạo
    @JsonProperty("username")
    private String username;
}
