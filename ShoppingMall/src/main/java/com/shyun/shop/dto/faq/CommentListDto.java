package com.shyun.shop.dto.faq;

import java.time.LocalDateTime;

import com.shyun.shop.entity.FaqComment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentListDto {
	private Long id;
	private String comment;
	private String commenter;
	private LocalDateTime updateTime;
	
	public CommentListDto(FaqComment e) {
		this.id = e.getId();
		this.comment = e.getComment();
		this.commenter = e.getCommenter();
		this.updateTime=e.getUpdateTime();
	}
	
	

}
