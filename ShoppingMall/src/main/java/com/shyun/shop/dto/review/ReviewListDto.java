package com.shyun.shop.dto.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.shyun.shop.constant.Rating;
import com.shyun.shop.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewListDto {
	private Long id;
	private String title;
	private String writer;
	private String content;
	private int readCount;
	private Rating rating;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	private List<Long> ReviewImgIds = new ArrayList<>(); //상품의 이미지 아이디 저장리스트  수정시 이미지 아이디를 담아둘용도
	private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>(); //상품 저장후 수정할때 이미지 정보를 저장하는 리스트

	public ReviewListDto() {
	}
	 public ReviewListDto(Review e) {
		  this.id=e.getId();
		  this.title= e.getTitle();
		  this.writer= e.getWriter();
		  this.rating=e.getRating();
		  this.regTime=e.getRegTime();
		  this.updateTime=e.getUpdateTime();
		  this.content=e.getContent();
		  this.reviewImgDtoList = e.getReviewImgList().stream().map(ReviewImgDto::new).collect(Collectors.toList());
	}
	 
		
		
	 private static ModelMapper modelMapper = new ModelMapper();
		
	    public Review createReview(){
	        return modelMapper.map(this, Review.class);
	    }
		
		public static ReviewListDto of(Review review) {
			return  modelMapper.map(review, ReviewListDto.class);
		}
		
}
