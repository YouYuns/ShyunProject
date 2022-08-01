package com.shyun.shop.dto.review;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.shyun.shop.constant.Rating;
import com.shyun.shop.dto.FileData;
import com.shyun.shop.entity.Item;
import com.shyun.shop.entity.Review;
import com.shyun.shop.entity.ReviewImg;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ReviewInsertDto{
	private Long id;
	@NotBlank(message = "제목은 필수 입력값입니다.")
	private String title;
	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;
	private String writer;
	@Enumerated(EnumType.STRING)
	private Rating rating;
	
	
	
	private List<Long> ReviewImgIds = new ArrayList<>(); //상품의 이미지 아이디 저장리스트  수정시 이미지 아이디를 담아둘용도
	
	private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>(); //상품 저장후 수정할때 이미지 정보를 저장하는 리스트
	
	private static ModelMapper modelMapper = new ModelMapper();
	
    public Review createReview(){
        return modelMapper.map(this, Review.class);
    }
	
	public static ReviewInsertDto of(Review review) {
		return  modelMapper.map(review, ReviewInsertDto.class);
	}
	
	public Review toReview() {
		return Review.builder()
				.title(title)
				.content(content).writer(writer)
				.rating(rating)
				.item(Item.builder().id(id).build())
				.build();
	}


	
}