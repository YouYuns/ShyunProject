package com.shyun.shop.entity;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
public class Address {
	
	private String roadAddress;
	private String postCode;
	private String detailAddress;
	
}
