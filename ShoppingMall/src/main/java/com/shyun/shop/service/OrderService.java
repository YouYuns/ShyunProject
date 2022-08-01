package com.shyun.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.shyun.shop.dto.OrderDto;
import com.shyun.shop.dto.OrderHistDto;

public interface OrderService {

	Long order(OrderDto orderDto, String email);

	Page<OrderHistDto> getOrderList(String email, Pageable pageable);

	boolean validateOrder(Long orderId, String email);

	void cancelOrder(Long orderId);

	Long orders(List<OrderDto> orderDtoList, String email);

}