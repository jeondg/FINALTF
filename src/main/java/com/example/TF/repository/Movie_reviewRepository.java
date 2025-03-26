package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_review;

public interface Movie_reviewRepository extends JpaRepository<Movie_review, Integer>{
	// 전체 리스트
	@Query(value ="select * from (select rownum rn, tt.* from \r\n"
			+ "(select * from movie_review order by logtime desc) tt)\r\n"
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_review> findByStartnumAndEndnum_logtime(@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 최신순 + 등급 조인
	@Query(value =
	    "SELECT * FROM (" +
	    "  SELECT rownum rn, rr.* FROM (" +
	    "    SELECT r.reviewcode, r.moviecode, r.user_id, r.review_comment, r.rating, r.logtime, m.grade " +
	    "    FROM movie_review r " +
	    "    JOIN movie_member m ON r.user_id = m.id " +
	    "    WHERE r.moviecode = :moviecode " +
	    "    ORDER BY r.logtime DESC" +
	    "  ) rr WHERE rownum <= :end" +
	    ") WHERE rn >= :start",
	    nativeQuery = true)
	List<Object[]> findReviewWithGradePaging(@Param("moviecode") int moviecode,
	                                         @Param("start") int start,
	                                         @Param("end") int end);

	// 공감순 정렬
	@Query(value =
	    "SELECT * FROM (" +
	    "  SELECT rownum rn, rr.* FROM (" +
	    "    SELECT r.reviewcode, r.moviecode, r.user_id, r.review_comment, r.rating, r.logtime, m.grade, COUNT(l.likecode) like_count " +
	    "    FROM movie_review r " +
	    "    JOIN movie_member m ON r.user_id = m.id " +
	    "    LEFT JOIN movie_review_like l ON r.reviewcode = l.reviewcode " +
	    "    WHERE r.moviecode = :moviecode " +
	    "    GROUP BY r.reviewcode, r.moviecode, r.user_id, r.review_comment, r.rating, r.logtime, m.grade " +
	    "    ORDER BY like_count DESC, r.logtime DESC" +
	    "  ) rr WHERE rownum <= :end" +
	    ") WHERE rn >= :start",
	    nativeQuery = true)
	List<Object[]> findReviewSortedByLike(@Param("moviecode") int moviecode,
	                                      @Param("start") int start,
	                                      @Param("end") int end);

		
	@Query(value = "SELECT COUNT(*) FROM movie_review WHERE moviecode = :moviecode AND user_id = :user_id", nativeQuery = true)
		int countByMoviecodeAndUser_id(@Param("moviecode") int moviecode,
		                               @Param("user_id") String user_id);
	
	// 리뷰 수 카운트
	@Query("SELECT COUNT(r) FROM Movie_review r WHERE r.moviecode = :moviecode")
	int countReviewsByMoviecode(@Param("moviecode") int moviecode);
}
