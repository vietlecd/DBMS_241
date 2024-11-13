package com.project.shopapp.DTO;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements Serializable {
    private String status;
    private String message;
    private String url;
}
