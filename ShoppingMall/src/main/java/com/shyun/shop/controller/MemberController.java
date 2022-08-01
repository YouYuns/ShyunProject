package com.shyun.shop.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shyun.shop.dto.MemberFormDto;
import com.shyun.shop.entity.Member;
import com.shyun.shop.service.MemberService;
import com.shyun.shop.service.MailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberController {
	
	private final MemberService memberService;
	private final MailService mailService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/members/new")
	public String memberForm(Model m) {
		
		m.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	
	@PostMapping("/members/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult result, Model m, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			return "member/memberForm";
		}
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			m.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		redirectAttributes.addFlashAttribute("success",memberFormDto.getName() + "님 회원가입을 축하합니다. 로그인 후 이용하세요!");
		return "redirect:/";
	}
	
	@GetMapping("/members/login")
	public String loginMember() {
		return "member/memberLoginForm";
	}
	
	@GetMapping("/members/login/error")//시큐리티에서 적용한 로그인 실패시 이동할 페이지
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "/member/memberLoginForm";
	}
	//이메일 ajax 중복확인
	@ResponseBody 
	@PostMapping("/members/emailCheck")
	public boolean emailCheck(String email) {
		return memberService.emailCheck(email);
	}
	
	
	//이메일 인증메일 확인
	@ResponseBody
	@PostMapping("/request-key/mail")
	public long requestMailKey(String email) {
		System.out.println(email);
		return mailService.mailSend(email);
	}
	
	@ResponseBody
	@GetMapping("/request-key/getKey")
	public String requestMailKey(HttpSession session) {
		System.out.println("마지막 접속시간 : " + session.getLastAccessedTime());
		System.out.println("생성시간 : " + session.getCreationTime());
		System.out.println("유지시간 : " + session.getMaxInactiveInterval());
		return (String) session.getAttribute("mailKey");
	}
	
	@ResponseBody
	@GetMapping("/request-key/remove")
	public void requestRemove(HttpSession session) {
		session.removeAttribute("mailKey");
	}
	
}
