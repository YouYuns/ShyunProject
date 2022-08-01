package com.shyun.shop.dto.faq;

import com.shyun.shop.entity.Faq;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class FaqUpdateDto {
	private String title;
	private String content;
	
	
	public Faq toEntity() {
		return Faq.builder()
				.title(title)
				.content(content)
				.build();
	}
	
}
