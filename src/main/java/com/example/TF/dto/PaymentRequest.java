package com.example.TF.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private String orderId;
    private String paymentKey;
    private int amount;
    private String orderType; // 영화(movie) or 스토어(store)

    // ✅ 추가된 필드
    private Long screeningId;
    private String seatNumber;
    private String userId;
}
