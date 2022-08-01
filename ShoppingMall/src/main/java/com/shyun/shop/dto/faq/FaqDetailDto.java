package com.shyun.shop.dto.faq;

import com.shyun.shop.constant.Division;
import com.shyun.shop.entity.Faq;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class FaqDetailDto {
	private long faqNo;
	
	private String title;
	private String writer;
	private String content;
	
	private Division division;

	public FaqDetailDto(Faq e) {
		this.faqNo = e.getId();
		this.title = e.getTitle();
		this.writer = e.getWriter();
		this.content = e.getContent();
		this.division = e.getDivision();
	}
	
	
}
