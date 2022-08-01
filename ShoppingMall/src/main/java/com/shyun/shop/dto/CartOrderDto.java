package com.shyun.shop.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOrderDto {
	private Long cartItemId;
	
	//장바구니에서 여러개 상품 주문해서 자기자신을 List
	private List<CartOrderDto> cartOrderDtoList;

}
