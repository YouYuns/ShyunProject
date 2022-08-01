package com.shyun.shop.dto;

import com.shyun.shop.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

//조회주문데이터 화면에 보낼Dto
@Getter @Setter
public class OrderItemDto {
	
	private String itemNm; //상품명
	private int count; //주문수량
	private int orderPrice; //주문금액
	private String imgUrl; //상품 이미지 경로
	
	//생성자로 orderItem객체와 이미지경로를 파라미터로받아서 멤버변수값세팅
	public OrderItemDto(OrderItem orderItem, String imgUrl) {
		this.itemNm = orderItem.getItem().getItemNm();
		this.count = orderItem.getCount();
		this.orderPrice = orderItem.getOrderPrice();
		this.imgUrl = imgUrl;
	}
	
}
