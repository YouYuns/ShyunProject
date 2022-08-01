package com.shyun.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.shyun.shop.dto.CartDetailDto;
import com.shyun.shop.dto.CartItemDto;
import com.shyun.shop.dto.CartOrderDto;
import com.shyun.shop.dto.OrderDto;
import com.shyun.shop.entity.Cart;
import com.shyun.shop.entity.CartItem;
import com.shyun.shop.entity.Item;
import com.shyun.shop.entity.Member;
import com.shyun.shop.repository.CartItemRepository;
import com.shyun.shop.repository.CartRepository;
import com.shyun.shop.repository.ItemRepository;
import com.shyun.shop.repository.MemberRepository;
import com.shyun.shop.service.CartService;
import com.shyun.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
	
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderService orderService;
	
	@Override
	public Long addCart(CartItemDto cartItemDto, String email) {
		Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new); //장바구니에 담을 상품엔티티 조회
		Member member = memberRepository.findByEmail(email); //현재로그인 회원 엔티티조회
		
		Cart cart = cartRepository.findByMemberId(member.getId()); //로그인한 회원의 장바구니 조회
		
		if(cart == null) { //장바구니가 처음일경우 생성 
			cart = Cart.createCart(member);
			cartRepository.save(cart);
		}
		
		CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
		//현재 상품이장바구니에 있는지 조회 
		
		if(savedCartItem != null) { //있으면 갯수만 늘리기
			savedCartItem.addCount(cartItemDto.getCount());
			return savedCartItem.getId();
		}else { //없으면 장바구니 엔티티, 상품엔티티, 수량 CartItem 엔티티 생성
			CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
			cartItemRepository.save(cartItem); //장바구니에 들어갈 상품저장
			return cartItem.getId();
		}
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CartDetailDto> getCartList(String email){
		List<CartDetailDto> cartDetailDtoList = new ArrayList<>();
		
		Member member= memberRepository.findByEmail(email); 
		
		Cart cart = cartRepository.findByMemberId(member.getId());
		if(cart == null) {
			return cartDetailDtoList;
		}
		
		cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
		
		return cartDetailDtoList;
	}
	//현재로그인한 회원의 해당장바구니 상품을 저장한 회원이같은지 검사
	@Override
	@Transactional(readOnly = true)
	public boolean validateCartItem(Long cartItemId, String email) {
		Member curMember = memberRepository.findByEmail(email); //현재로그인 회원조회
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
		Member savedMember = cartItem.getCart().getMember(); //장바구니 저장한회원을 조회
		//현재 로그인한 회원과 장바구니 상품을 저장한 회원이 다를경우 False, 같으면True
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		return true;
	}
	
	@Override
	public void updateCartItemCount(Long cartItemId, int count) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
		cartItem.updateCount(count);
	}
	
	
	//주문로직 호출 
	@Override
	public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
		List<OrderDto> orderDtoList = new ArrayList<>();
		for(CartOrderDto cartOrderDto : cartOrderDtoList) {
			CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
			
			OrderDto orderDto = new OrderDto();
			orderDto.setItemId(cartItem.getItem().getId());
			orderDto.setCount(cartItem.getCount());
			orderDtoList.add(orderDto);
		}
		//카트에있는 Dto로 상품명 상품개수 하나씩 orderDtolist에 담음
		Long orderId = orderService.orders(orderDtoList, email); //장바구니에 담음 상품 주문
		
		//주문한상품 장바구니에서 제거
		for(CartOrderDto cartOrderDto : cartOrderDtoList) {
			CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
					.orElseThrow(EntityNotFoundException::new);
			cartItemRepository.delete(cartItem);
		}
		return orderId;
	}
	//장바구니취소할시 삭제
    @Override
	public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
	
}
