package com.shyun.shop.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.shyun.shop.constant.CategoryA;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Category {
	
	@Column(name = "category_no")
	@Id
	private long no;	
	
	public Category createNo() {
		no = cateA.code + code;
		return this;
	}
	
	
	private String name;
	private long code;
	
	@Enumerated(EnumType.STRING)
	private CategoryA cateA;
	
	@Builder.Default
	@ManyToMany(mappedBy = "categories")
	Set<Item> items = new HashSet<>();
	
	
}
