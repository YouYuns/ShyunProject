package com.shyun.shop.dto.faq;


import com.shyun.shop.constant.Division;
import com.shyun.shop.entity.Faq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqInsertDto {
	private Long faqNo;
	
	private String title;
	private String writer;
	private String content;
	
	private String defImgName;
	private String addImgName;
	
	private Division division;
	
	public Faq toEntity() {
		return Faq.builder()
				.title(title).writer(writer).content(content).division(division)
				.build();
	}
}
