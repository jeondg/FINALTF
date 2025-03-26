package com.example.TF.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.TF.entity.Booking;
import com.example.TF.entity.Screening;
import com.example.TF.repository.BookingRepository;
import com.example.TF.repository.ScreeningRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class BookingApiController {
	@Autowired
    BookingRepository bookingRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    /**
     * ✅ 좌석 선택 페이지에서 영화 가격 조회 (screeningId 사용)
     */
    @GetMapping("/api/screening/price")
    public ResponseEntity<Map<String, Integer>> getScreeningPrice(@RequestParam("screeningId") Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId).orElse(null);
        if (screening == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("price", 0));
        }
        return ResponseEntity.ok(Collections.singletonMap("price", screening.getMovie().getPrice()));
    }

    /**
     * ✅ 결제 페이지에서 예약된 가격 조회 (bookingId 사용)
     */
    @GetMapping("/api/booking/price")
    public ResponseEntity<Map<String, Integer>> getBookingPrice(@RequestParam("bookingId") Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("price", 0));
        }
        return ResponseEntity.ok(Collections.singletonMap("price", booking.getPrice()));
    }
    
    @GetMapping("/api/booking/bookingId")
    public ResponseEntity<Map<String, Long>> getBookingIdByOrderId(@RequestParam("orderId") String orderId) {
        Booking booking = bookingRepository.findByOrderIdIgnoreCase(orderId);
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("bookingId", 0L));
        }
        return ResponseEntity.ok(Collections.singletonMap("bookingId", booking.getId()));
    }

    /**
     * ✅ orderId로 bookingId 조회 (결제 완료 후 사용)
     */
    @GetMapping("/api/booking/orderId")
    public ResponseEntity<Map<String, String>> getOrderIdByBookingId(@RequestParam("bookingId") Long bookingId) {
        System.out.println("📌 요청된 bookingId: " + bookingId);

        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null || booking.getOrderId() == null) {
            System.out.println("❌ [ERROR] 해당 bookingId의 orderId가 없습니다: " + bookingId);
            return ResponseEntity.status(404).body(Collections.singletonMap("orderId", ""));
        }

        System.out.println("✅ [SUCCESS] bookingId: " + bookingId + " → orderId: " + booking.getOrderId());
        return ResponseEntity.ok(Collections.singletonMap("orderId", booking.getOrderId()));
    }
    
    @GetMapping("/api/check-login-status")
    @ResponseBody
    public Map<String, Boolean> checkLoginStatus(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        return Collections.singletonMap("loggedIn", loggedIn != null && loggedIn);
    }


}


