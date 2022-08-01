package com.shyun.shop.dto.review;

import java.time.LocalDateTime;


import com.shyun.shop.constant.Rating;
import com.shyun.shop.entity.Review;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReviewUpdateDto {
	private String content;
	private String title;
	private Rating rating;
	
	private String link;

	public Review toEntity() {
		return Review.builder()
				.title(title)
				.content(content)
				.rating(rating)
				.build();
	}
	
	
	
}
