package com.shyun.shop.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyun.shop.dto.CartDetailDto;
import com.shyun.shop.dto.CartItemDto;
import com.shyun.shop.dto.CartOrderDto;
import com.shyun.shop.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@PostMapping("/cart")
	@ResponseBody
	public ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder(); //StringBuilder 변경가능한 문자열  String 합치기가능
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			} //cartItemDto valid 에러있는지 검사 장바구니 상품담을 상품정보 
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		String email = principal.getName();
		Long cartItemId;
		
		try {
			cartItemId = cartService.addCart(cartItemDto, email);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
	}
	
	//장바구니페이지 이동
	@GetMapping("/cart")
	public String orderHist(Principal principal, Model model) {
		List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
		model.addAttribute("cartItems", cartDetailList);
		return "cart/cartList";
		
	}
	//상품 수량 업데이트 ajax
	@PatchMapping("/cartItem/{cartItemId}")
	@ResponseBody
	public ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal) {
		if(count <= 0) {
			return new ResponseEntity<String>("최소 1개 이상담아주세요", HttpStatus.BAD_REQUEST);
		}else if(!cartService.validateCartItem(cartItemId, principal.getName())) {
			return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
		}
		
		cartService.updateCartItemCount(cartItemId, count);
		return new ResponseEntity<Long>(cartItemId,HttpStatus.OK);
	}
	
	@PostMapping("/cart/orders")
	@ResponseBody
	public ResponseEntity orderCartItem	(@RequestBody CartOrderDto cartOrderDto, Principal principal) {
		List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
		
		if(cartOrderDtoList == null || cartOrderDtoList.size() == 0) { //주문상품선택여부확인
			return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
		}
		
		for(CartOrderDto cartorder : cartOrderDtoList) {
			if(!cartService.validateCartItem(cartorder.getCartItemId(), principal.getName())) {
				return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
			}
		}
		
		Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
		//주문 로직 호출 결과 생성된 주문번호를 반환 받습니다.
		return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	}
	@ResponseBody
    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal){

        if(!cartService.validateCartItem(cartItemId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
	
}