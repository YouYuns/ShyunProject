package com.shyun.shop.dto.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.shyun.shop.constant.ItemSellStatus;
import com.shyun.shop.entity.Item;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDto {
	
	private Long id;
	
	@NotBlank(message = "상품명은 필수 입력 값입니다.")
	private String itemNm; //상품명
	
	@NotBlank(message = "상품 설명은 필수 입력 값입니다.(10자 이상)")
	private String itemDetail; //상품설명
	
	
	@Max(value = 100 ,message = "할인율이 100%초과가 될 수 없습니다.")
	@Min(value = 0, message = "할인율이 0%미만이 될 수 없습니다.")
	private Integer discount;//할인율
	
	private boolean rate;
	
	@NotNull(message = "가격은 필수 입력 값입니다.")
	private Integer price; //상품가격
	
	
	@NotNull(message = "재고는 필수 입력 값입니다.")
	private Integer stockNumber;
	
	@NotBlank(message = "제조사는 필수 입력 값입니다.")
	private String brand;
	
	private long caNo;
	
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus;
	
	private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); //상품 저장후 수정할때 이미지 정보를 저장하는 리스트
	
	private List<Long> itemImgIds = new ArrayList<>(); //상품의 이미지 아이디 저장리스트  수정시 이미지 아이디를 담아둘용도
	
	
	public Item toEntity() {
		if(rate) {
			discount=price*discount/100;
		}
		return Item.builder()
				.itemNm(itemNm).price(price).discount(discount).stockNumber(stockNumber).itemDetail(itemDetail)
				.build();
	}
	
	private static ModelMapper modelMapper = new ModelMapper();
	
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
	
	public static ItemFormDto of(Item item) {
		return modelMapper.map(item, ItemFormDto.class);
	}
	
}
