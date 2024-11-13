package com.project.shopapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.Serializable;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements Serializable {
    private String orderInfo;

//    public String getPaymentTime() {
//        return paymentTime.substring(0, 4) + "-" + paymentTime.substring(4, 6) + "-" +
//                paymentTime.substring(6, 8) + " " + paymentTime.substring(8, 10) + ":" +
//                paymentTime.substring(10, 12) + ":" + paymentTime.substring(12, 14);
//    }
    @JsonProperty("paymentTime")
    private String paymentTime;
    private String transactionId;
    private String totalPrice;


}
