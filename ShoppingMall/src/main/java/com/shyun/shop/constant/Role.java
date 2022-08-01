package com.shyun.shop.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	ADMIN("ROLE_ADMIN","관리자"),
	USER("ROLE_USER","회원");
	
	public final String roleName;
	public final String title;
	
}
