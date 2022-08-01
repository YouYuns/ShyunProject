package com.shyun.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //Auditing 기능활성화
@Configuration
public class AuditConfig {
	
	//등록사 수정자 처리를 해주는 AuditorAware빈으로 등록
	@Bean
	public AuditorAware<String> auditorProvider(){
		return new AuditorAwareImpl();
	}

}
