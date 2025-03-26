package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.TF.entity.Screening;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
	// 영화 ID, 극장 ID, 날짜로 특정 상영 정보 조회
	@Query("SELECT s FROM Screening s WHERE s.movie.id = :movieId AND s.theater.id = :theaterId AND TO_CHAR(s.screenTime, 'YYYY-MM-DD') = :date")
	List<Screening> findByMovieAndTheaterAndDate(
	        @Param("movieId") Long movieId,
	        @Param("theaterId") Long theaterId,
	        @Param("date") String date
	);
}
