package com.shyun.shop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shyun.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	//로그인한 사용자 주문데이터 페이징조건에 맞춰서 조회
	@Query("select o from Order o where o.member.email = :email order by o.orderDate desc")
	List<Order> findOrders(@Param("email") String email, Pageable pageable);
	
	//로그인한 사용자 주문데이터 몇개인지 조회
	@Query("select count(o) from Order o where o.member.email = :email")
	Long countOrder(@Param("email") String email);
	
}
