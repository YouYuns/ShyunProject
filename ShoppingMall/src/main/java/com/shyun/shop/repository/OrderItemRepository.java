package com.shyun.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyun.shop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
