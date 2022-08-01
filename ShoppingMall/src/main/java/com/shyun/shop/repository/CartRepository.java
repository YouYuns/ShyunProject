package com.shyun.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyun.shop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
		//현재로그인한 회원의 Cart엔티티 찾기
	Cart findByMemberId(Long memberId);
}
