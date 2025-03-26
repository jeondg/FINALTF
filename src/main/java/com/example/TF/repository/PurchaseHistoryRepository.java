package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TF.entity.PurchaseHistory;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {
    List<PurchaseHistory> findByUserIdOrderByPurchaseDateDesc(String userId);
}
