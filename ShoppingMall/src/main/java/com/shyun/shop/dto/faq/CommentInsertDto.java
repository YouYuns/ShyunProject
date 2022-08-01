package com.shyun.shop.dto.faq;

import com.shyun.shop.entity.Faq;
import com.shyun.shop.entity.FaqComment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInsertDto {
	private Long id;
	private Faq faqId;
	private String comment;
	private String commenter;
	public FaqComment toEntity() {
		
		return FaqComment.builder()
				.comment(comment)
				.commenter(commenter)
				.faq(faqId)
				.build();
				
	}
	
	
	
}
