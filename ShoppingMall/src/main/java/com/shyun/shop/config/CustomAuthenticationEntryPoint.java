package com.shyun.shop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			
		
		//ajax  x-requested-with헤더는해당요청이 ajax일때 가져온다 그값은 XMLHttpRequest로 세팅되어오는데 -> 인증되지않은사용자가 요청해서오면 Unauthorized에러를 발생시킨다
		if("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
		}else {
			response.sendRedirect("/members/login");
		}
	}

}
