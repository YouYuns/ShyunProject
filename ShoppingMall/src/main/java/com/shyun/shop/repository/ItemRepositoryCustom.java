package com.shyun.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.shyun.shop.dto.MainItemDto;
import com.shyun.shop.dto.item.ItemSearchDto;
import com.shyun.shop.entity.Item;

public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
	
	Page<MainItemDto> getMainItemPage(Pageable pageable);

}
