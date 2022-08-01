package com.shyun.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyun.shop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Member findByEmail(String email);

	Optional<Member> findByEmailAndIsDeletedAndIsSocial(String username, boolean b, boolean c);
	
}
