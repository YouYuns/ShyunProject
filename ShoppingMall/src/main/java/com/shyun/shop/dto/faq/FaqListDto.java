package com.shyun.shop.dto.faq;


import com.shyun.shop.constant.Division;
import com.shyun.shop.entity.Faq;

import lombok.Getter;

@Getter 
public class FaqListDto {
	
	private long faqNo;
	
	private String title;
	private String content;
	private String writer;
	private String question;//질문
	private String answer; //응답E
	
	private Division division;
	
	public FaqListDto(Faq e){
		faqNo=e.getId();
		title= e.getTitle();
		writer=e.getWriter();
		content=e.getContent();
		division=e.getDivision();
	}

}
