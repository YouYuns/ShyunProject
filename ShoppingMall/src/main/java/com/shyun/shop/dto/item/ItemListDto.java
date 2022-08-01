package com.shyun.shop.dto.item;

import java.time.LocalDateTime;

import com.shyun.shop.entity.Item;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ItemListDto {
	private Long id;
	private String itemNm;
	private Integer price;
	private String itemDetail;
	private Integer discount;
	private String brand;
	
	private String defImgUrl;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	
	
	public ItemListDto(Item e) {
		this.id = e.getId();
		this.itemNm = e.getItemNm();
		this.price = e.getPrice();
		this.itemDetail = e.getItemDetail();
		this.discount = e.getDiscount();
		this.brand = e.getBrand();
		this.regTime = e.getRegTime();
		this.updateTime = e.getUpdateTime();
		e.getImgs().forEach(img->{
			if(img.getRepimgYn().equals("Y")) {
				defImgUrl=img.getImgUrl();
			}
		});
	}
	
}
