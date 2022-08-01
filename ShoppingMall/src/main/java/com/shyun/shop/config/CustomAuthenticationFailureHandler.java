package com.shyun.shop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	//Bean : class 이름 위에  @Component
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		
		
//		String msg = "인증오류";
//		if(exception instanceof UsernameNotFoundException) {
//			msg="회원이 아니거나 탈퇴회원입니다.";
//		}else if (exception instanceof BadCredentialsException) {
//			msg="비밀번호 오류";
//		}
		setDefaultFailureUrl("/login?error");
		System.out.println(exception);
		super.onAuthenticationFailure(request, response, exception);
	}
	
	

}
