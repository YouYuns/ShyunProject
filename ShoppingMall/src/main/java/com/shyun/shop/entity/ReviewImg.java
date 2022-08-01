package com.shyun.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Table(name = "review_img")
@Entity
public class ReviewImg extends BaseEntity{
		
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id @Column(name = "review_img_id")
	private Long id;
	
	private String imgName; //이미지 파일명
	
	private String oriImgName; //원본 이미지 파일명
	
	private String imgUrl; //이미지 조회 경로
	
	
	@JoinColumn(name = "review_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Review review;

	//이미지정보 업데이트 메소드
	public void updateItemImg(String imgName, String oriImgName, String imgUrl) {
		this.imgName = imgName;
		this.oriImgName = oriImgName;
		this.imgUrl = imgUrl;
	}
}
