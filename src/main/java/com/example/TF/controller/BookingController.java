package com.example.TF.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.TF.dto.PaymentRequest;
import com.example.TF.entity.Booking;
import com.example.TF.entity.Screening;
import com.example.TF.service.BookingService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingController {

    @Autowired
    BookingService service;

    @GetMapping("/booking")
    public String bookingPage(Model model) {
        model.addAttribute("movies", service.getAllMovies());
        model.addAttribute("theaters", service.getAllTheaters());
        return "/main/booking";
    }

    @GetMapping("/booking/screenings")
    @ResponseBody
    public List<ScreeningDTO> getScreenings(@RequestParam("movieId") Long movieId,
                                            @RequestParam("theaterId") Long theaterId,
                                            @RequestParam("date") String date) {
        List<Screening> screenings = service.getScreeningsByMovieAndTheater(movieId, theaterId, date);
        return screenings.stream()
                .map(s -> new ScreeningDTO(s.getId(), s.getScreenTime().toString().substring(11, 16)))
                .collect(Collectors.toList());
    }

    public static class ScreeningDTO {
        private Long id;
        private String screenTime;

        public ScreeningDTO(Long id, String screenTime) {
            this.id = id;
            this.screenTime = screenTime;
        }

        public Long getId() {
            return id;
        }

        public String getScreenTime() {
            return screenTime;
        }
    }

    @GetMapping("/select-seat")
    public String selectSeatPage(@RequestParam("screeningId") Long screeningId, Model model, HttpSession session) {
        Screening screening = service.findScreeningById(screeningId);
        if (screening == null) return "error";

        String memId = (String) session.getAttribute("memId");
        if (memId == null) return "redirect:/member/loginForm";

        model.addAttribute("screening", screening);
        model.addAttribute("userId", memId);
        return "/seat/select-seat";
    }

    @GetMapping("/booking/booked-seats")
    @ResponseBody
    public List<Booking> getBookedSeats(@RequestParam("screeningId") Long screeningId) {
        return service.getBookedSeats(screeningId);
    }

    @PostMapping("/booking/reserve")
    @ResponseBody
    public String reserveSeat(@RequestParam("screeningId") Long screeningId,
                              @RequestParam("seatNumber") String seatNumber,
                              @RequestParam("userId") String userId) {
        Booking savedBooking = service.reserveSeat(screeningId, seatNumber, userId);
        if (savedBooking == null) throw new RuntimeException("좌석 예약에 실패했습니다.");
        return String.valueOf(savedBooking.getId());
    }

    @GetMapping("/price")
    public ResponseEntity<Map<String, Integer>> getBookingPrice(@RequestParam("bookingId") Long bookingId) {
        Booking booking = service.findBookingById(bookingId);
        if (booking == null) return ResponseEntity.badRequest().body(Collections.singletonMap("price", 0));
        return ResponseEntity.ok(Collections.singletonMap("price", booking.getPrice()));
    }

    @GetMapping("/payments")
    public String paymentsPage(@RequestParam("bookingId") Long bookingId, Model model) {
        Booking booking = service.findBookingById(bookingId);
        if (booking == null) return "error";

        model.addAttribute("price", booking.getPrice());
        model.addAttribute("bookingId", bookingId);
        return "/pay/payments";
    }

    @PostMapping("/confirm")
    @ResponseBody
    public ResponseEntity<?> confirmPayment(@RequestBody PaymentRequest request) {
        try {
            Booking booking = service.findByOrderId(request.getOrderId());
            if (booking == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("status", "fail"));
            }
            try {
                service.updateBookingStatus(booking.getId(), request.getPaymentKey());
                return ResponseEntity.ok(Collections.singletonMap("status", "success"));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("status", "seat_conflict"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("status", "fail"));
        }
    }
}