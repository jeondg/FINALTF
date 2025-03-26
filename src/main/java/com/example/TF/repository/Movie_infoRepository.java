package com.example.TF.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TF.entity.Movie_info;

public interface Movie_infoRepository extends JpaRepository<Movie_info, Integer>{
	// 장르별 데이터 검색
	@Query(value="select count(*) as cnt from movie_info where genre = :genre", nativeQuery = true)
	int count_genre(@Param("genre") String genre);
	// 국내검색
	@Query(value="select count(*) as cnt from movie_info where country = '한국'", nativeQuery = true)
	int count_country();	
	// 해외검색
	@Query(value="select count(*) as cnt from movie_info where country not like '한국'", nativeQuery = true)
	int count_foreign();
	
	// 영화번호순 
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info order by moviecode desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_moviecode(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);			
	
	// 장르별 관객순
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info where genre =:genre order by total desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_genrerank(
			@Param("genre") String genre,
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);		
	
	// 국내 관객순
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info where country ='한국' order by total desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_countryrank(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);		
	
	// 해외 관객순
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info where country not like '한국' order by total desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_foreignrank(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 박스오피스 순(전일관객수 순)
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info order by yesterday desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_yesterday(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);	
	
	// 누적 관객 내림차순 리스트 
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info order by total desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_total(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 개봉일 순
	@Query(value = "select * from (select rownum rn, tt.* from "
			+ "(select * from movie_info order by releasedate desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Movie_info> findByStartnumAndEndnum_releasedate(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);	
	
	// 현재 상영작
	@Query(value ="select * from ("
			+ "    select rownum rn, tt.* from ("
			+ "        select * from movie_info "
			+ "        where releasedate <= sysdate"
			+ "        order by releasedate desc"
			+ "    ) tt"
			+ ")\r\n"
			+ "where rn >= :startNum and rn <= :endNum;", nativeQuery = true)
	List<Movie_info> findByStartNumAndEndnum_nowshowing(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
	// 개봉 예정작
	@Query(value ="select * from ("
			+ "    select rownum rn, tt.* from ("
			+ "        select * from movie_info "
			+ "        where releasedate > sysdate"
			+ "        order by releasedate asc"
			+ "    ) tt"
			+ ")"
			+ "where rn >= :startNum and rn <= :endNum;", nativeQuery = true)
	List<Movie_info> findByStartNumAndEndnum_willshow(
			@Param("startNum") int startNum,
			@Param("endNum") int endNum);
	
}
