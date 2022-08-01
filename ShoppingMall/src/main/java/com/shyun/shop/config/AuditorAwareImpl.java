package com.shyun.shop.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//AuditorAware 인터페이스 구현하면 특정 필드에 지금 로그인한 사람의정보로 등록자 수정자로 자동으로 등록하게해준다
public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		//Authentication : SecurityContext의 user,인증 정보를 가지고 있다 -> Authentication에서 정보를 가져온다
		//SecurityContextHolder -> 누가 인증했는지에 대한정보들을 저장
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId="";
		
		if(authentication != null) {
			userId = authentication.getName();
		}
		//optional의 of메서드를 통해 객체 생성
		return Optional.of(userId);
	}

}
