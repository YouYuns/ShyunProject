package com.shyun.shop.dto.faq;

import com.shyun.shop.entity.FaqComment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {
	private Long id;
	private Long pcid;
	private String comment;
	private String commenter;
	
	
	public FaqComment toEntity() {
		return FaqComment.builder()
				.id(id)
				.pcid(pcid)
				.comment(comment)
				.commenter(commenter)
				.build();
	}
	
	
	
	
}
