package com.shyun.shop.dto.item;

import com.shyun.shop.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;



/**
 * 조회조건 - 상품등록일, 상품판매상태, 상품명 또는 등록자 아이디
 */
@Getter
@Setter
public class ItemSearchDto {
	private String searchDateType;
	
	private ItemSellStatus searchSellStatus;
	
	private String searchBy; //itemNm:상품명 / createdBy : 상품등록자
	
	private String searchQuery =""; //조회할 검색어 변수
	
	
	

}
