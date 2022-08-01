package com.shyun.shop.dto.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shyun.shop.constant.Rating;
import com.shyun.shop.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewDetailDto {
	private Long id;
	private Rating rating;
	private String title;
	private String writer;
	private String content;
	private List<Long> ReviewImgIds = new ArrayList<>(); //상품의 이미지 아이디 저장리스트  수정시 이미지 아이디를 담아둘용도
	private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>(); //상품 저장후 수정할때 이미지 정보를 저장하는 리스트
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	
	 public ReviewDetailDto(Review review) {
		  this.id=review.getId();
		  this.regTime = review.getRegTime();
		  this.title = review.getTitle();
		  this.content = review.getContent();
		  this.writer= review.getWriter();
		  this.rating=review.getRating();
		  this.updateTime = review.getUpdateTime(); 
		  this.reviewImgDtoList = review.getReviewImgList().stream().map(ReviewImgDto::new).collect(Collectors.toList());
	}
}
