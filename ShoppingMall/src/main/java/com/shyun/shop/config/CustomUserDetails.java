package com.shyun.shop.config;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.shyun.shop.entity.Member;

import lombok.Getter;



@Getter
public class CustomUserDetails extends User implements OAuth2User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String email;
	private String name;
	

	//Super 클래스에 super() 존재하지 않고 다른 생성자(오버로딩생성자)만 존재하는 경우 생성자 명시 
	public CustomUserDetails(Member e ) {
		
		super(e.getEmail(), e.getPassword(), e.getRoleSet().stream()
							.map(role->new SimpleGrantedAuthority(role.roleName))
							.collect(Collectors.toSet()));
		//Set<MemberRole> -> 로 변경해야된다 -> Collection<? extends GrantedAuthority> authorities 이너머을 이 타입으로 넣어줘라해서  
		//SimpleGrantedAuthority는 GrantedAuthority 중에 하나이다 grantedauthority타입중하나인 simplegratedauthority로 넣어줌
		
		email = e.getEmail();
		name = e.getName();
	}

 
	@Override
	public Map<String, Object> getAttributes() {
		return null; //이거 안쓸거라서 그냥 널해도 된다 
	}
	
	
}
