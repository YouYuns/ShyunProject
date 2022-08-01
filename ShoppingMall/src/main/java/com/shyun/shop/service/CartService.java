package com.shyun.shop.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.shyun.shop.dto.CartDetailDto;
import com.shyun.shop.dto.CartItemDto;
import com.shyun.shop.dto.CartOrderDto;

public interface CartService {

	Long addCart(CartItemDto cartItemDto, String email);

	List<CartDetailDto> getCartList(String email);

	//현재로그인한 회원의 해당장바구니 상품을 저장한 회원이같은지 검사
	boolean validateCartItem(Long cartItemId, String email);

	void updateCartItemCount(Long cartItemId, int count);

	//주문로직 호출 
	Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email);

	//장바구니취소할시 삭제
	void deleteCartItem(Long cartItemId);

}