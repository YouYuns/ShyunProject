package com.shyun.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	private final CustomOAuth2UserService customOAuth2UserService;
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorize -> 
			authorize
				.antMatchers("/", "/members/**", "/item/**", "/images/**"
				,"/common/**","/request-key/*","/customer/**","/ws/**","/faq/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				);
				
		//인증되지않은 사용자가 접근할때 exceptionHandling으로 수행되는 핸들러 등록 -> 만들어놨던 CustemAuthenticationEntryPoint
		  http.exceptionHandling()
          .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	//소셜 로그인  로그인페이지 /login userInfoEndpoint-> userService 메서드로 customOAuth2UserService 
		  http.oauth2Login(oauth2Login -> oauth2Login
					.loginPage("/login")
					.userInfoEndpoint().userService(customOAuth2UserService));
			
		
		//http.formLogin();
		//로그인페이지 설정
		http.formLogin(formLogin -> formLogin
				.loginPage("/members/login") //GET 로그인페이지이동 
				.usernameParameter("email")
				.passwordParameter("password")
				//.failureUrl("/login?error") //ERROR PAGE //이렇게 써도되고 아래 주석에 핸들러만들어서 사용해도된다 
				.loginProcessingUrl("/members/login") //Form태그눌렀을때 POST / Security가 처리해주는 url -> UserDetails
				.defaultSuccessUrl("/", false) //로그인 성공시 무조건 여기로 보낸다 false는 상황별로 다르다 
				//.successHandler(successHandler)
				//.failureHandler(failureHandler()) //로그인 실패시 처리
				.permitAll()
		);
		http.
			logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 URL를 설정합니다.
			.logoutSuccessUrl("/"); //로그아웃 성공 시 이동할 URL을 설정
		
		http.csrf();
		
		return http.build();
	}
	
	@Bean
	 WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().antMatchers("/css/**"
	        											, "/js/**"
	        											,"/img/**"
	        											,"/favicon.ico"
	        											,"/error"
	        											,"/resources/**");
	    }
	



}
