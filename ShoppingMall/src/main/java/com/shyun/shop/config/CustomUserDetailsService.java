package com.shyun.shop.config;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyun.shop.entity.Member;
import com.shyun.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
	//DaoAuthenticationProvider. 
	
	//DAO : jpa-Repository
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//db에 회원존재하는지 체크 존재하면 UserDetails 으로 세팅해서 리턴
		Optional<Member> result=memberRepository.findByEmailAndIsDeletedAndIsSocial(email, false, false);
		//isDeleted : true는 탈퇴회원, false가 정상회원
		//IsSocial : false 일반회원, True 소셜가입회원
		
		if(result.isEmpty()) {
			throw new UsernameNotFoundException("존재하지 않거나 탈퇴회원");
		}
		
		//UserDetails 타입으로 리턴 User활용		
		return new CustomUserDetails(result.get());
	}
	
}
