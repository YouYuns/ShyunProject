package com.shyun.shop.dto.review;

import org.modelmapper.ModelMapper;

import com.shyun.shop.entity.ItemImg;
import com.shyun.shop.entity.ReviewImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewImgDto {
	
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	
	public ReviewImgDto() {
		// TODO Auto-generated constructor stub
	}
	
	//멤버변수로 ModelMapper객체를추가
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//ItemImg엔티티 객체 파라미터 -> 자료형과멤버변수의이름이 같으면 ItemImgDto로 값을 복사해서 반환
	public static ReviewImgDto of(ReviewImg reviewImg) {
		return modelMapper.map(reviewImg, ReviewImgDto.class);
	}


	public ReviewImgDto(ReviewImg e) {
		this.id = e.getId();
		this.imgName = e.getImgName();
		this.oriImgName = e.getOriImgName();
		this.imgUrl = e.getImgUrl();
	}
	

}
