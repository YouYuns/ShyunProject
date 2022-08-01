package com.shyun.shop.dto.item;

import org.modelmapper.ModelMapper;

import com.shyun.shop.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemImgDto {
	
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repImyn;

	//멤버변수로 ModelMapper객체를추가
	private static ModelMapper modelMapper = new ModelMapper();
	
	
	//ItemImg엔티티 객체 파라미터 -> 자료형과멤버변수의이름이 같으면 ItemImgDto로 값을 복사해서 반환
	public static ItemImgDto of(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDto.class);
	}
	

}
