package com.shyun.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Table(name = "cart")
@Entity
public class Cart extends BaseEntity {

	@Column(name = "cart_id")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@JoinColumn(name = "member_id") //매핑할 외래키를 지정
	@OneToOne(fetch = FetchType.LAZY)
	private Member member;
	
	//회원한명단 한개의 장바구니 생성
	public static Cart createCart(Member member) {
		Cart cart = new Cart();
		cart.setMember(member);
		return cart;
	}
	
}
