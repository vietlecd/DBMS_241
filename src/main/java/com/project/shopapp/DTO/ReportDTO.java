package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    @JsonProperty("reportcontent")
    private String reportContent;

    @JsonProperty("reportDate")
    private String reportDate;

    @JsonProperty("username")
    private String username;
}
