package com.example.TF.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "purchase_history") // ✅ 실제 DB 테이블 이름
@SequenceGenerator(
    name = "purchase_seq_generator",
    sequenceName = "purchase_history_seq", // ✅ Oracle SEQUENCE 이름
    initialValue = 1,
    allocationSize = 1
)
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq_generator")
    @Column(name = "purchase_seq") // ✅ 테이블 컬럼명과 매핑
    private Long purchaseSeq;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "product_seq", nullable = false)
    private int productSeq;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Column(name = "purchase_qty", nullable = false)
    private int purchaseQty;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "purchase_date")
    private Date purchaseDate = new Date(); // 기본값 SYSDATE와 동일한 효과

    @Column(name = "payment_status", length = 30)
    private String paymentStatus = "결제완료"; // 기본값
}
