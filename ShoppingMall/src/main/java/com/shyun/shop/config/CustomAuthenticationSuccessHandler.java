package com.shyun.shop.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override //기억하는 객체 원래 url
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		setDefaultTargetUrl("/");
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null) {
			String redirectUrl = savedRequest.getRedirectUrl();
			System.out.println("원래 이동하고자 하는 페이지 주소: " + redirectUrl);
			redirectStrategy.sendRedirect(request, response, redirectUrl);
		}else {
			redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
		}
		
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	
		
}
