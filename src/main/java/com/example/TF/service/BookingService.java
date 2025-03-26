package com.example.TF.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TF.entity.*;
import com.example.TF.repository.*;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    Movie_MemberRepository movieMemberRepository;
    @Autowired
    Seat_service seatService;

    private static final Map<String, Integer> levelBonusMap = Map.of(
        "Basic-Silver", 1000,
        "Silver-Gold", 2000,
        "Gold-VIP", 3000,
        "VIP-VVIP", 4000
    );

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public List<Screening> getScreeningsByMovieAndTheater(Long movieId, Long theaterId, String date) {
        return screeningRepository.findByMovieAndTheaterAndDate(movieId, theaterId, date);
    }

    public Screening findScreeningById(Long screeningId) {
        return screeningRepository.findById(screeningId).orElse(null);
    }

    public Booking findBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    public Booking findByOrderId(String orderId) {
        return bookingRepository.findByOrderIdIgnoreCase(orderId);
    }

    public List<Booking> getBookedSeats(Long screeningId) {
        return bookingRepository.findByScreeningId(screeningId);
    }

    @Transactional
    public Booking reserveSeat(Long screeningId, String seatNumbers, String userId) {
        Screening screening = findScreeningById(screeningId);
        if (screening == null) throw new RuntimeException("상영 정보 없음");

        List<Booking> existing = bookingRepository.findByScreeningId(screeningId);
        Set<String> alreadyBooked = new HashSet<>();
        for (Booking b : existing) {
            if ("PAID".equalsIgnoreCase(b.getPaymentStatus())) {
                alreadyBooked.addAll(Arrays.asList(b.getSeatNumber().split(",")));
            }
        }

        for (String seat : seatNumbers.split(",")) {
            if (alreadyBooked.contains(seat)) {
                throw new RuntimeException("이미 예매된 좌석입니다: " + seat);
            }
        }

        int moviePrice = screening.getMovie().getPrice();
        int totalPrice = moviePrice * seatNumbers.split(",").length;

        Booking booking = new Booking();
        booking.setScreening(screening);
        booking.setSeatNumber(seatNumbers);
        booking.setUserId(userId);
        booking.setPrice(totalPrice);
        booking.setPaymentStatus("pending");
        booking.setOrderId("ORD-" + UUID.randomUUID().toString().substring(0, 8));
        booking.setMovieTitle(screening.getMovie().getTitle());

        bookingRepository.save(booking);

        int screenNum = 1;
        int theaterCode = screening.getTheater().getId().intValue();
        seatService.markSeatsAsPurchased(screenNum, theaterCode, parseSeatPositions(seatNumbers));

        return booking;
    }

    @Transactional
    public void updateBookingStatus(Long bookingId, String paymentKey) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        List<Booking> existing = bookingRepository.findByScreeningId(booking.getScreening().getId());
        Set<String> bookedSeats = new HashSet<>();
        for (Booking b : existing) {
            if (!b.getId().equals(booking.getId()) && "PAID".equalsIgnoreCase(b.getPaymentStatus())) {
                bookedSeats.addAll(Arrays.asList(b.getSeatNumber().split(",")));
            }
        }
        for (String seat : booking.getSeatNumber().split(",")) {
            if (bookedSeats.contains(seat)) {
                throw new RuntimeException("이미 예약된 좌석");
            }
        }
        booking.setPaymentStatus("PAID");
        booking.setPaymentKey(paymentKey);
        bookingRepository.save(booking);

        // 포인트 적립 처리
        Optional<Movie_Member> opt = movieMemberRepository.findById(booking.getUserId());
        if (opt.isPresent()) {
            Movie_Member member = opt.get();
            int earned = (booking.getPrice() / 1500) * 10;
            int current = Optional.ofNullable(member.getPoint()).orElse(0);
            int currentVip = Optional.ofNullable(member.getVippoint()).orElse(0);

            String before = getGradeByVipPoint(currentVip);
            int updatedVip = currentVip + earned;
            String after = getGradeByVipPoint(updatedVip);

            member.setPoint(current + earned);
            member.setVippoint(updatedVip);
            member.setGrade(after);

            if (!before.equals(after)) {
                String key = before + "-" + after;
                Integer bonus = levelBonusMap.get(key);
                if (bonus != null) {
                    member.setPoint(member.getPoint() + bonus);
                }
            }
            movieMemberRepository.save(member);
        }
    }

    public List<int[]> parseSeatPositions(String seatNumbers) {
        return Arrays.stream(seatNumbers.split(","))
                .map(seat -> {
                    int y = seat.charAt(0) - 'A';
                    int x = Integer.parseInt(seat.substring(1)) - 1;
                    return new int[]{x, y};
                }).collect(Collectors.toList());
    }

    public String getGradeByVipPoint(int vipPoint) {
        if (vipPoint >= 5000) return "VVIP";
        else if (vipPoint >= 2000) return "VIP";
        else if (vipPoint >= 1000) return "Gold";
        else if (vipPoint >= 500) return "Silver";
        else return "Basic";
    }
}