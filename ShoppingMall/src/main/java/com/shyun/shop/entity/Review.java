package com.shyun.shop.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.shyun.shop.constant.Rating;
import com.shyun.shop.dto.review.ReviewUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Review extends BaseEntity{
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(columnDefinition = "text not null") //text : oracle-CLOB
	private String content;

	@Column(nullable = false)
	private String writer;
	
	private int readCount;
	
	@Enumerated(EnumType.STRING)
	private Rating rating;
	
	@JoinColumn(name = "item_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	@Builder.Default
	@ElementCollection
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewImg> reviewImgList = new ArrayList<>();
	
	//수정하기 위한 메서드 title과 content
	public Review update(ReviewUpdateDto reviewUpdateDto) {
		this.title =reviewUpdateDto.getTitle();
		this.content=reviewUpdateDto.getContent();
		this.rating=reviewUpdateDto.getRating();
		return this;
	}
	
}
