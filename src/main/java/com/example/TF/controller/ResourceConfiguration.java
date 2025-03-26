package com.example.TF.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class ResourceConfiguration implements WebMvcConfigurer{
	// 파일 저장 폴더의 경로 저장
	// application.properties파일의 project.upload.path 값을 읽어와서 저장시킨다.
	@Value("${project.upload.path}")
	private String uploadpath;
	@Value("${project.upload.movie_poster.path}") //D:/JDG/spring_boot/workspace/static/movie_poster
	private String movie_posteruploadpath;
	@Value("${project.upload.movie.path}") //D:/JDG/spring_boot/workspace/static/movie
	private String movieuploadpath;	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/storage/**")
		.addResourceLocations("file:///" + uploadpath + "/");

		// http://localhost:8080/storage/cupra.jpg
		registry.addResourceHandler("/movie_poster/**")
				.addResourceLocations("file:///" + movie_posteruploadpath + "/");//마지막 / 생략하지마
		//"file:///D:/JDG/spring_boot/workspace/static/movie_poster/"
		
		registry.addResourceHandler("/movie/**")
		.addResourceLocations("file:///" + movieuploadpath + "/");//마지막 / 생략하지마
		//"file:///D:/JDG/spring_boot/workspace/static/movie/"
	}
	
	
}
