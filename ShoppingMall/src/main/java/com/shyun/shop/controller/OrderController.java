package com.shyun.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyun.shop.dto.OrderDto;
import com.shyun.shop.dto.OrderHistDto;
import com.shyun.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	
	//@RequestBody:Http요청 본문 body에 담긴 내용을 자바객체로전달 @ReponseBody :자바객체를 HTTP요청을 body로전달
	//HttpEntity클래스를상속받 구현클래스중 하나 ResponseEntity - HTTP요청(request)또는 응답(reponse)해당하는 body header포함 클래스
	//Principal컨트롤러 처리기 메소드에서 자동파라미터로 주입받을수있는 타입중하나인데 getName으로 아이디정도만 받아올수있음
	@ResponseBody
	@PostMapping("/order")
	public ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			//StringBuilder 변경가능한 여러 문자열을 만들어 줌
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrores = bindingResult.getFieldErrors();
			for(FieldError fieldError : fieldErrores) {
				sb.append(fieldError.getDefaultMessage());
			}
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); 
			//에러 정보를 ResponseEntity객체 담아서 보냄
			
		}
		
		String email = principal.getName();
		Long orderId;
		
		try {
			orderId = orderService.order(orderDto, email);
			//화면으로넘어오는 주문정보(orderDto)와 이메일정보를 가지고 주문로직을호출
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Long>(orderId,HttpStatus.OK);
		//결과값으로 생성된 주문번호와 요청이 성공했다는 HTTP 응답상태코드 반환
	}
	
	
	  @GetMapping(value = {"/orders", "/orders/{page}"})
	    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){

	        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
	        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

	        model.addAttribute("orders", ordersHistDtoList);
	        model.addAttribute("page", pageable.getPageNumber());
	        model.addAttribute("maxPage", 5);

	        return "order/orderHist";
	    }
	  
	  
	  @PostMapping("/order/{orderId}/cancel")
	  public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {
		  
		  //다른사람이 주문 취소하지 못하도록 주문취소 권한검사
		  if(!orderService.validateOrder(orderId, principal.getName())) {
			  return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
		  }
		  //주문취소
		  orderService.cancelOrder(orderId);
		  return new ResponseEntity<Long>(orderId, HttpStatus.OK);
	  }
	  
	  
	  
}
