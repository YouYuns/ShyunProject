package com.shyun.shop.service.impl;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.shyun.shop.service.MailService;

import lombok.RequiredArgsConstructor;

@Service
public class MailServiceImpl implements MailService{
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private HttpSession session;
	//*
	@Override
	public long mailSend(String email) {
		
		
		//이메일체크
		//System.out.println(mailSender);
		
		
		SimpleMailMessage message=new SimpleMailMessage(); //simplemailmessage 텍스트 보낼수있는 객체
		String code=createKey();
		message.setFrom("yytjdgh0312@gmail.com");//보내는메일주소
		message.setTo(email);//메일을 받을 주소
		message.setSubject("[(주)SHY]회원가입을 위한 인증메일입니다.");
		//message.setText("회원가입을위한 인증번호입니다. 인증번호 : "+ System.nanoTime());
		message.setText("회원가입을위한 인증번호입니다. 인증번호 : "+ code);
		
		mailSender.send(message);
		//System.out.println("code:"+code);
		session.setMaxInactiveInterval(60*5);//5분
		session.setAttribute("mailKey", code);
		//System.out.println(code);
		return System.currentTimeMillis(); //인증코드 리턴
	
		
		//entity객체 생성
		//Verification entity=new Verification(email, code);
		//저장
		//repository.save(entity);
		
	}
	//*/
	private String createKey() {
		StringBuffer key=new StringBuffer();
		Random random=new Random();
		for(int i=0; i < 8; i++) {
			int idx=random.nextInt(3);//0~2
			
			switch(idx) {
			case 0://영문자 소문자//a(97)~z(122) 26글자
				key.append((char)(random.nextInt(26)+97)); //0+97 : a
				break;
			case 1://영문자 대문자//A(65)~Z(90) 26
				key.append((char)(random.nextInt(26)+65)); //0+65 : A
				break;
			case 2://숫자
				key.append(random.nextInt(10)); //0~9
			}
		}
		return key.toString();
	}

	/*
	@Override
	public String mailCheck(MailRequestDto dto) {
		Optional<Verification> op=repository.findByEmailAndCode(dto.getEmail(), dto.getCode());
		if(op.isPresent()) {
			return "인증되었습니다.";
		}else {
			return "인증번호가 다릅니다.";
		}
	}
	//*/

}
