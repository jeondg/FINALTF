package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findByScreeningId(Long screeningId);
    List<Booking> findByUserId(String userId);
    
    // ✅ bookingId로 예약 조회
    Booking findById(long bookingId);

    // ✅ orderId로 예약 조회 (대소문자 무시)
    Booking findByOrderIdIgnoreCase(String orderId);
}
