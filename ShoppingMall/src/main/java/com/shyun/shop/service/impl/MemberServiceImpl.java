package com.shyun.shop.service.impl;

import java.security.Principal;
import java.util.Optional;

import org.h2.engine.SysProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.shyun.shop.entity.Member;
import com.shyun.shop.repository.MemberRepository;
import com.shyun.shop.service.MemberService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService { 
//UserDetailService 인터페이스는 DB에서 회원정보를 가져오는 역할을 담당 (loadUserByUserName()메소드 존재하고 UserDetails 인터페이스를 반환)
	
	private final MemberRepository memberRepository;
	
	
	@Override
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		
		return memberRepository.save(member);
	}

	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		if(findMember !=null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		}
	}

	@Override
	public boolean emailCheck(String email) {
		Member result=memberRepository.findByEmail(email);
		if(result == null) {
			return true; //이미 존재하는 경우: 사용불가
		}
		return false;
	}


	
}
