package com.shyun.shop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.shyun.shop.constant.Division;
import com.shyun.shop.dto.faq.FaqUpdateDto;
import com.shyun.shop.dto.review.ReviewUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Faq extends BaseEntity{

	@Id @Column(name = "faq_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String content;
	private String writer;
	private int readCount;
	
	@Builder.Default
	@OneToMany(mappedBy = "faq")
	private List<FaqComment> faqComments = new ArrayList<>(); 
	
	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	@Column(name = "divs")
	@Enumerated(EnumType.STRING)
	private Division division; 

	//수정하기 위한 메서드 title과 content
	public Faq update(FaqUpdateDto dto) {
		this.title =dto.getTitle();
		this.content=dto.getContent();
		return this;
	}
	
	
}
