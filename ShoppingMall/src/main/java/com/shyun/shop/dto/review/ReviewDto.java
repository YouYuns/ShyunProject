package com.shyun.shop.dto.review;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.querydsl.core.annotations.QueryProjection;
import com.shyun.shop.constant.Rating;
import com.shyun.shop.dto.FileData;
import com.shyun.shop.entity.Item;
import com.shyun.shop.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
public class ReviewDto extends FileData {
	private long id;
	
	@NotBlank(message = "내용은 필수 입력 값입니다.")
	private String content;
	@NotBlank(message = "내용은 필수 입력 값입니다.")
	private String title;
	private String writer;
	private int readCount;
	
	@Enumerated(EnumType.STRING)
	private Rating rating;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	

	public void addFileData(FileData fileData) {
		url = fileData.getUrl();
		orgName = fileData.getOrgName();
		size = fileData.getSize();
	}
	
	public Review toEntity() {
		return Review.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.rating(rating)
				.item(Item.builder().id(id).build())
				.build();
	}
	
	
	
}
