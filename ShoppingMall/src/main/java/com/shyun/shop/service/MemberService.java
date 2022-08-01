package com.shyun.shop.service;

import java.security.Principal;

import org.springframework.ui.Model;

import com.shyun.shop.entity.Member;

public interface MemberService {

	Member saveMember(Member member);

	boolean emailCheck(String email);



}