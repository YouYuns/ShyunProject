package com.shyun.shop.dto;

import com.shyun.shop.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	private long no;	
	
	private String name;
	private long code;
	
	public CategoryDto(Category e) {
		this.no = e.getNo();
		this.name = e.getName();
		this.code = e.getCode();
	}
	
	
}
