package com.example.TF.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TF.entity.Screen_seat;
import com.example.TF.repository.Screen_seat_repository;

import jakarta.transaction.Transactional;

@Service
public class Seat_service {
	
	@Autowired
	Screen_seat_repository seat_repository;
	
	public void screen_seat_save(int screen_num, int theater_code,
			int row, int col, int seat_code, String purchase) {
		// 좌석 정보를 DB에 저장
		Screen_seat screen_seat = new Screen_seat();
		screen_seat.setScreen_num(screen_num);
		screen_seat.setTheater_code(theater_code);
		screen_seat.setY_index(row);
		screen_seat.setX_index(col);
		screen_seat.setSeat_code(seat_code);
		screen_seat.setPurchase(purchase);
		// 화면의 상영관 코드, 극장 코드 등을 설정하여 저장
		seat_repository.save(screen_seat);
	}
	
	public List<Screen_seat> seat_list(int screen_num, int theater_code) {
		return seat_repository.findByScreen_numAndTheater_code(screen_num, theater_code);
	}
	
	@Transactional
	public void screen_seat_delete(int screen_num, int theater_code) {
		seat_repository.deleteByScreen_numAndTheater_code(screen_num, theater_code);
	}
	
	// 예매한 좌석 구매 상태 업데이트 (수민)
	@Transactional
	public void markSeatsAsPurchased(int screenNum, int theaterCode, List<int[]> positions) {
	    List<Screen_seat> seatList = seat_list(screenNum, theaterCode);

	    for (int[] pos : positions) {
	        for (Screen_seat seat : seatList) {
	            if (seat.getX_index() == pos[0] && seat.getY_index() == pos[1]) {
	                seat.setPurchase("Y");
	                seat_repository.save(seat);
	            }
	        }
	    }
	}
}

