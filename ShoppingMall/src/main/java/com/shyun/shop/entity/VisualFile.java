package com.shyun.shop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import com.shyun.shop.dto.visual.VisualUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class VisualFile {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vno;
	
	private String link; //이미지 클릭하며 이동하는 페이지
	
	private String url;
	
	private String orgName;
	
	private long size;
	
	private String title;
	
	private String sub;
	
	private long num;
	
	private boolean isShow;

	public VisualFile updateIsShow(boolean isShow) {
		this.isShow=isShow;
		return this;
	}

	public VisualFile updateTitle(String title) {
		this.title = title;
		return this;
	}

	public VisualFile updateSub(String sub) {
		this.sub=sub;
		return this;
	}
	public VisualFile updateData(VisualUpdateDto dto) {
		//this.isShow=dto.isShow();
		
		this.title=dto.getTitle();
		this.sub=dto.getSub();
		this.link=dto.getLink();
		return this;
	}

}
