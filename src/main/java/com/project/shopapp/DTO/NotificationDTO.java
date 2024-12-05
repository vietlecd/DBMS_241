package com.project.shopapp.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
   /* @JsonProperty("markID")
    private Long markID; // ID của bookmark*/

    @JsonProperty("nid")
    private Integer nid;
    @JsonProperty("messagedate")
    private String messagedate; // Thời gian tạo
    @JsonProperty("messagecontent")
    private String messagecontent;
}
