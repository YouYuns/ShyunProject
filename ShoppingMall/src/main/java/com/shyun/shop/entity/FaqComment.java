package com.shyun.shop.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.internal.build.AllowSysOut;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class FaqComment extends BaseEntity{
	@Id @Column(name = "faq_comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long pcid;
	private String comment;
	private String commenter;
	
	@JoinColumn(name = "faq_id")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Faq faq;
}
